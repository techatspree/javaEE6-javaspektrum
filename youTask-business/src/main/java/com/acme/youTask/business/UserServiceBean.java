package com.acme.youTask.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.dao.UserDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class UserServiceBean implements UserService {

  @Inject
  private Logger log;

  @EJB
  private UserDao userDao;

  @EJB
  private TaskDao taskDao;

  // -------------- interface method implementations ------------------------------------

  @Override
  public User loadById(final Long userId) {
    return userDao.load(userId);
  }

  @Override
  public User loadByUsername(final String username) {
    return userDao.loadByUsername(username);
  }

  @Override
  public void saveUser(User user) {
    userDao.persist(user);
  }

  @Override
  public void mergeUser(User user) {
    userDao.persist(user);
  }

  @Override
  public void removeUser(User user) {
    log.debugv("removing user {0}", user.getLogin());

    List<Task> tasksToRemove = taskDao.findForUser(user);

    if (!tasksToRemove.isEmpty()) {
      log.debugv("user {0} has {1} tasks which have to be deleted as well", user.getLogin(), tasksToRemove.size());

      for (Task task : tasksToRemove) {
        taskDao.delete(task);
      }
    }

    userDao.delete(loadById(user.getId()));
  }

  @Override
  public List<User> loadAll() {
    return userDao.loadAll();
  }

}
