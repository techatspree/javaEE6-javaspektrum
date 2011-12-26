package com.acme.youTask.dao.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Eine auf {@link EntityManager} basierende DAO-Implementierung.
 * 
 * @author <a href="mailto:marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz</a>
 * 
 * @param <E>
 *          Die Klasse, die vom DAO behandelt wird.
 * 
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 */
public abstract class JpaGenericDao<E extends Serializable> implements GenericDao<E> {

  /**
   * dient als cache, somit nur über getter zugreifen.
   */
  private transient Class<E> persistentClass;

  @PersistenceContext
  private EntityManager entityManager;

  // ------------------- Implementierte Interface-Methoden
  // --------------------

  @Override
  public void persist(final E e) {
    this.entityManager.persist(e);
  }

  @Override
  public E merge(final E entity) {
    final E mergedEntity = this.entityManager.merge(entity);
    this.entityManager.flush();
    this.entityManager.refresh(mergedEntity);
    return mergedEntity;
  }

  @Override
  public void delete(final E e) {
    this.entityManager.remove(e);
  }

  @Override
  public E load(final Long id) {
    assert id != null : "id != null";
    return this.entityManager.find(getPersistentClass(), id);
  }

  @Override
  public boolean exists(final Long id) {
    return load(id) != null;
  }

  @Override
  public List<E> loadAll() {
    final Class<E> persistenceClass = getPersistentClass();
    final TypedQuery<E> query = this.entityManager
        .createQuery("SELECT e FROM " + persistenceClass.getSimpleName() + " e", persistenceClass);
    return query.getResultList();
  }

  // ----------------------------- Getter & Setter
  // ----------------------------

  /**
   * Stellt die Persistence-Klasse zur Verfügung.
   * 
   * @return die Persistence-Klasse.
   */
  @SuppressWarnings("unchecked")
  protected Class<E> getPersistentClass() {
    if (persistentClass == null) {
      persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    return persistentClass;
  }

  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  // ---------------------------- Komfort-Methoden
  // ----------------------------

  /**
   * Führt das JPA-QL-Statement aus und sorgt für genau ein Element.
   * 
   * @param stmt
   *          das Statement
   * @return das eine Objekt.
   */
  protected E loadByQuery(final String stmt) {
    return this.entityManager.createQuery(stmt, getPersistentClass()).getSingleResult();
  }

  /**
   * Führt das JPA-QL-Statement aus und sorgt für genau ein Element.
   * 
   * @param stmt
   *          das Statement.
   * @param parameters
   *          die Parameter.
   * @param maxResults
   *          die maximale Anzal Zeilen.
   * @return eine Liste von Elementen.
   */
  protected List<E> findByQuery(final String stmt, final Map<String, Object> parameters, final int maxResults) {

    checkMaxResult(maxResults);

    final TypedQuery<E> query = this.entityManager.createQuery(stmt, getPersistentClass());
    addParameters(query, parameters);
    return query.setMaxResults(maxResults).getResultList();
  }

  /**
   * Führt das JPA-QL-Statement <tt>stmt</tt> mit den Parametern <tt>parameters</tt> aus.
   * 
   * @param stmt
   *          das JPA-QL-Statement.
   * @param parameters
   *          die Parameter
   * @return eine Liste der Objekte.
   */
  protected List<E> findByQuery(final String stmt, final Map<String, Object> parameters) {
    final TypedQuery<E> query = this.entityManager.createQuery(stmt, getPersistentClass());
    addParameters(query, parameters);
    return query.getResultList();
  }

  /**
   * Erstellt ein NamedQuery mit dem Namen <tt>queryName</tt>.
   * 
   * @param queryName
   *          der Name des Queries.
   * @param parameters
   *          die Parameter
   * @return das Object
   */
  protected E loadByNamedQuery(final String queryName, final Map<String, Object> parameters) {
    final TypedQuery<E> namedQuery = this.entityManager.createNamedQuery(queryName, getPersistentClass());
    addParameters(namedQuery, parameters);
    return namedQuery.getSingleResult();
  }

  /**
   * Erstellt ein NamedQuery mit dem Namen <tt>queryName</tt>.
   * 
   * @param queryName
   *          der Name des Queries.
   * @param parameters
   *          die Parameter
   * @return das Object
   */
  protected E findSingleByNamedQuery(final String queryName, final Map<String, Object> parameters) {
    final TypedQuery<E> namedQuery = this.entityManager.createNamedQuery(queryName, getPersistentClass());
    addParameters(namedQuery, parameters);
    List<E> resultList = namedQuery.getResultList();
    return resultList.isEmpty() ? null : resultList.get(0);
  }

  /**
   * Führt das NamedQuery mit den Parametern aus.
   * 
   * @param queryName
   *          der Name des Querys
   * @param parameters
   *          die Parameter.
   * @return die Liste der Elemente.
   */
  protected List<E> findByNamedQuery(final String queryName, final Map<String, Object> parameters) {
    final TypedQuery<E> query = this.entityManager.createNamedQuery(queryName, getPersistentClass());
    addParameters(query, parameters);
    return query.getResultList();
  }

  /**
   * Führt das NamedQuery <tt>queryName</tt> mit den Parametern <tt>parameters</tt> aus und liefert ab der Zeile <tt>firstResult</tt> aber
   * maximal <tt>maxResults</tt> Zeilen.
   * 
   * @param queryName
   *          der Name des Querys
   * @param parameters
   *          die Parameter
   * @param firstResult
   *          die Nummer der ersten Zeile
   * @param maxResults
   *          die maximale Anzahl der Zeilen
   * @return eine Liste der Objekte.
   */
  public List<E> findByNamedQuery(final String queryName, final Map<String, Object> parameters, final int firstResult, final int maxResults) {
    checkMaxResult(maxResults);

    final TypedQuery<E> query = this.entityManager.createNamedQuery(queryName, getPersistentClass());
    addParameters(query, parameters).setFirstResult(firstResult);
    return query.setMaxResults(maxResults).getResultList();
  }

  /**
   * Führt ein SQL-Statement aus.
   * 
   * @param stmt
   *          das SQL-Statement
   * @param parameters
   *          die Parameter.
   * @return eine Liste der Objekte.
   */
  @SuppressWarnings("unchecked")
  protected List<Object> findByNativeQuery(final String stmt, final Map<String, Object> parameters) {
    return addParameters(this.entityManager.createNativeQuery(stmt), parameters).getResultList();
  }

  /**
   * Führt das NamedQuery mit executeUpdate aus.
   * 
   * @param queryName
   *          Name des NamedQuerys.
   * @param parameters
   *          die zu setzenden Parameter.
   * @return die Anzahl der aktuallisierten Datensätze.
   */
  protected int executeUpdate(final String queryName, final Map<String, Object> parameters) {
    return addParameters(this.entityManager.createNamedQuery(queryName), parameters).executeUpdate();
  }

  // ---------------------------- Hilfsmethoden
  // -------------------------------

  private void checkMaxResult(final int maxResults) {
    if (maxResults < 1) {
      final String msg = MessageFormat.format("maxResults darf nicht kleiner als 1 sein [{0}]", Integer.valueOf(maxResults));
      throw new IllegalArgumentException(msg);
    }
  }

  private static Query addParameters(final Query stmt, final Map<String, Object> parameters) {
    if (null == parameters) {
      return stmt;
    }
    for (final Map.Entry<String, Object> entry : parameters.entrySet()) {
      stmt.setParameter(entry.getKey(), entry.getValue());
    }
    return stmt;
  }

  /**
   * 
   * @param params
   *          must be an even number of alternating arguments, where the first argument is a string (key) and the second an arbitrary object
   *          (value).
   * 
   * @return Map built from keys and values
   */
  public static Map<String, Object> createParameterMap(final Object... params) {
    final Map<String, Object> result = new HashMap<String, Object>();

    for (int i = 0; i < params.length; i += 2) {
      assert i + 1 < params.length : "Not enough parameters " + Arrays.asList(params);

      final Object key = params[i];
      final Object value = params[i + 1];

      assert key != null : "key != null";

      result.put(key.toString(), value);
    }

    return result;
  }
}
