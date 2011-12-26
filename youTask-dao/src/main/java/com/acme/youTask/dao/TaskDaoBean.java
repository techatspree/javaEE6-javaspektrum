package com.acme.youTask.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.acme.youTask.dao.common.JpaGenericDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class TaskDaoBean extends JpaGenericDao<Task> implements TaskDao {

  @Override
  public List<Task> findForCategory(Category category) {
    final Map<String, Object> paramMap = createParameterMap(Task.PARAM_CATEGORY, category);
    return findByNamedQuery(Task.QUERY_LOAD_FOR_CATEGORY, paramMap);
  }

  @Override
  public List<Task> findForUser(User user) {
    final Map<String, Object> paramMap = createParameterMap(Task.PARAM_USERNAME, user.getLogin());
    return findByNamedQuery(Task.QUERY_LOAD_FOR_USER, paramMap);
  }
}
