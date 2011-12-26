package com.acme.youTask.dao.common;

import java.util.List;

/**
 * Generisches Interface für verschiedene DAOs.
 * 
 * @author <a href="mailto:marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz</a>
 * 
 * @param <E>
 *          Klasse der Entity.
 * 
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 */
public interface GenericDao<E> {

  /**
   * Lädt die Entity mit der übergebenen Id.
   * 
   * @param id
   *          Die Id der gesuchten Entity.
   * @return die gesuchte Entity-Instanz oder null, falls mit der Id noch kein Datensatz existiert.
   */
  E load(Long id);

  /**
   * Lädt alle Entities vom verwalteten Typ.
   * 
   * @return Liste mit allen Datensätzen
   */
  List<E> loadAll();

  /**
   * Speichert die übergebene Entity. Falls diese bereits existiert, so erfolgt ein Update.
   * 
   * @param entity
   *          die zu speichernde Entity
   */
  void persist(E entity);

  /**
   * Übernimmt den Status des zu speichernden Entity's in ein neues Entity und speichert dieses.
   * 
   * @param entity
   *          das zu speichernde Entity.
   * @return das neue gespeicherte Entity.
   */
  E merge(E entity);

  /**
   * Löscht die übergebene Entity aus der Datenbank.
   * 
   * @param entity
   *          die zu löschende Entity.
   */
  void delete(E entity);

  /**
   * Prüft, ob ein Datensatz mit der übergebenen Id bereits existiert.
   * 
   * @param id
   *          die Id der zu prüfenden Entity
   * @return true falls ein Datensatz mit der Id exisitert, sonst false.
   */
  boolean exists(Long id);

}
