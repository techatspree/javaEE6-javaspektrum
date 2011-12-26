package com.acme.youTask.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class TaskServiceBean implements TaskService {
  
  @EJB
  private TaskDao taskDao;

  // -------------- interface method implementations ------------------------------------
  
  @Override
  public void saveTask(Task task) {
    taskDao.persist(task);
  }
  
  @Override
  public void mergeTask(Task task) {
    taskDao.persist(task);
  }

  @Override
  public void removeTask(Task task) {
    taskDao.delete(task);
  }

  @Override
  public List<Task> loadAll() {
    return taskDao.loadAll();
  }

  @Override
  public List<Task> loadForCategory(Category category) {
    return taskDao.findForCategory(category);
  }

  @Override
  public List<Task> loadForUser(User user) {
    return taskDao.findForUser(user);
  }


}
