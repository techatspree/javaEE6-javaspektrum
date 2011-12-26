package com.acme.youTask.dao;

import java.util.Map;

import javax.ejb.Stateless;

import com.acme.youTask.dao.common.JpaGenericDao;
import com.acme.youTask.domain.User;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class UserDaoBean extends JpaGenericDao<User> implements UserDao {

  @Override
  public User loadByUsername(String username) {
    final Map<String, Object> paramMap = createParameterMap(User.PARAM_USERNAME, username);
    return findSingleByNamedQuery(User.QUERY_LOAD_BY_USERNAME, paramMap);
  }

}
