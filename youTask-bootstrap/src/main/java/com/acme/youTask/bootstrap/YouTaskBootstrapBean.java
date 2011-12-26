package com.acme.youTask.bootstrap;

import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.dao.UserDao;
import com.acme.youTask.dao.common.GenericDao;
import com.acme.youTask.domain.AbstractEntity;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Singleton
@Startup
public class YouTaskBootstrapBean {
  @Inject
  private Logger log;

  // @Inject geht hier nicht wegen der Vererbung (GenericDao<E,ID>)!!! ->
  // Weld/CDI-Bug
  @EJB
  private TaskDao aufgabeDao;

  @EJB
  private UserDao userDao;

  @PostConstruct
  public void insert() {
    log.debug("Setup data in data base...");

    setupAufgabe();
    
    log.debug("Test data has been successfully set up");
  }

  private void setupAufgabe() {

    final User user = new User("xXx", "vin", "disel", "triple@xXx.com");
    
    final Task aufgabe1 = new Task("call my wife", Category.TODO, true, user);
    final Task aufgabe2 = new Task("rearrange office", Category.IDEA, false, user);
    final Task aufgabe3 = new Task("task management", Category.IDEA, false, user);
    final Task aufgabe4 = new Task("send offer", Category.TODO, false, user);
    final Task aufgabe5 = new Task("TaskForce", Category.READ, false, user);

    save(userDao, user);
    save(aufgabeDao, aufgabe1, aufgabe2, aufgabe3, aufgabe4, aufgabe5);
  }

  private <T extends AbstractEntity> void save(final GenericDao<T> dao, final T... entities) {
    for (final T entity : entities) {
      dao.persist(entity);
    }
  }
}
