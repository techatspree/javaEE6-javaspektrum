package com.acme.youTask.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.acme.youTask.business.TaskService;
import com.acme.youTask.business.UserService;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Named
@SessionScoped
public class TaskController implements Serializable {

  private static final long serialVersionUID = 1L;

  private Category categoryFilter;

  @Inject
  private Logger log;

  @Inject
  private TaskService taskService;

  @Inject
  private UserService uerService;

  private Task task;

  // -------------- initialization ------------------------------------------------------

  @PostConstruct
  public void init() {
    task = new Task();
  }

  // -------------- actions -------------------------------------------------------------

  public void saveTask() {

    // TODO get currently logged in user!
    final User trilpeX = uerService.loadByUsername("xXx");

    // initially new tasks are not finished
    task.setFinished(false);
    task.setUser(trilpeX);

    taskService.saveTask(task);

    task = new Task();
  }

  // -------------- controller helper ---------------------------------------------------

  public List<Task> getTasks() {
    List<Task> tasks = new ArrayList<Task>();
    final List<Task> all = taskService.loadAll();

    if (categoryFilter != null) {
      for (final Task aufgabe : all) {
        if (aufgabe.getCategory() == categoryFilter) {
          tasks.add(aufgabe);
        }
      }
    } else {
      tasks = all;
    }

    return tasks;
  }

  // -------------- getter / setter -----------------------------------------------------

  public Task getTask(){
    return task;
  }

  public Category getCategoryFilter() {
    return categoryFilter;
  }

  public void setCategoryFilter(final Category categoryFilter) {
    this.categoryFilter = categoryFilter;
  }

  public void filterChanged(final ValueChangeEvent event) {
    categoryFilter = (Category) event.getNewValue();
    log.debugv("category filter changed to", categoryFilter);
  }

}
