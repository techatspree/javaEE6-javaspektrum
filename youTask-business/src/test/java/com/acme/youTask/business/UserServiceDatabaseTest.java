package com.acme.youTask.business;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Test;

import com.acme.youTask.dao.AbstractYouTaskTest;
import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.dao.TaskDaoBean;
import com.acme.youTask.dao.UserDao;
import com.acme.youTask.dao.UserDaoBean;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

import de.akquinet.jbosscc.needle.annotation.InjectInto;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.db.transaction.VoidRunnable;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
public class UserServiceDatabaseTest extends AbstractYouTaskTest {

  @ObjectUnderTest
  @InjectInto(targetComponentId = "userService")
  private final UserDao userDao = new UserDaoBean();

  @ObjectUnderTest
  @InjectInto(targetComponentId = "userService")
  private final TaskDao taskDao = new TaskDaoBean();

  @ObjectUnderTest
  private final UserService userService = new UserServiceBean();
  
  // -------------- test methods --------------------------------------------------------
  
  @Test
  public void testUserService() throws Exception {
    
    final User user = new User("xXx", "vin", "disel", "triple@xXx.com");
    
    Assert.assertEquals(0, userService.loadAll().size());
    
    transactionHelper.executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(final EntityManager entityManager) throws Exception {
        userService.saveUser(user);
      }
    });
    
    Assert.assertEquals(1, userService.loadAll().size());
  }

  @Test
  public void testRemoveUser() throws Exception {

    Assert.assertEquals(0, userService.loadAll().size());
    
    // first create and save a user
    final User user1 = new User("xXx", "vin", "disel", "triple@xXx.com");
    final User user2 = new User("spiderman", "peter", "parker", "peter.parker@marvel.com");
    
    transactionHelper.executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(final EntityManager entityManager) throws Exception {
        userService.saveUser(user1);
        userService.saveUser(user2);
      }
    });
    
    final Task u1_task1 = new Task("aa1", Category.IDEA, false, user1);
    final Task u1_task2 = new Task("aa2", Category.PRIVATE, true, user1);
    final Task u1_task3 = new Task("aa3", Category.TODO, false, user1);

    final Task u2_task1 = new Task("bb1", Category.IDEA, false, user2);
    final Task u2_task2 = new Task("bb2", Category.WAITING, true, user2);
    
    transactionHelper.executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(final EntityManager entityManager) throws Exception {
        transactionHelper.persist(u1_task1);
        transactionHelper.persist(u1_task2);
        transactionHelper.persist(u1_task3);

        transactionHelper.persist(u2_task1);
        transactionHelper.persist(u2_task2);
      }
    });

    Assert.assertEquals(2, userService.loadAll().size());
    Assert.assertEquals(5, taskDao.loadAll().size());
    
    transactionHelper.executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(final EntityManager entityManager) throws Exception {
        userService.removeUser(user1);
      }
    });
    
    Assert.assertEquals(1, userService.loadAll().size());
    Assert.assertEquals(2, taskDao.loadAll().size());
    Assert.assertNull(taskDao.load(u1_task1.getId()));
    Assert.assertNotNull(taskDao.load(u2_task1.getId()));
    Assert.assertNotNull(taskDao.load(u2_task2.getId()));

  }

}
