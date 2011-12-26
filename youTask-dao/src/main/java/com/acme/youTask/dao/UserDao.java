package com.acme.youTask.dao;

import javax.ejb.Local;

import com.acme.youTask.dao.common.GenericDao;
import com.acme.youTask.domain.User;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Local
public interface UserDao extends GenericDao<User> {

  User loadByUsername(final String username);

}
