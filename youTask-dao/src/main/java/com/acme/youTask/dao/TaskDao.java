package com.acme.youTask.dao;

import java.util.List;

import com.acme.youTask.dao.common.GenericDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

import javax.ejb.Local;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Local
public interface TaskDao extends GenericDao<Task> {

  List<Task> findForCategory(Category category);

  List<Task> findForUser(User user);

}
