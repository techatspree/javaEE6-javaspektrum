package com.acme.youTask.business;

import static org.easymock.EasyMock.eq;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Rule;
import org.junit.Test;

import com.acme.youTask.common.testsupport.LogInjector;
import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.dao.UserDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import de.akquinet.jbosscc.needle.mock.EasyMockProvider;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
public class UserServiceEasymockTest  {

  @Rule
  public NeedleRule needleRule = new NeedleRule(new LogInjector());

  @ObjectUnderTest
  private final UserService userService = new UserServiceBean();
  
  private EasyMockProvider mockProvider = needleRule.getMockProvider();
  
  @Test
  public void testRemoveUserWithMockedServices() throws Exception {
    
    mockProvider.resetAllToStrict();
    
    final User user = new User("xXx", "vin", "disel", "triple@xXx.com");
    
    final TaskDao taskDao = needleRule.getInjectedObject(TaskDao.class);
    final UserDao userDao = needleRule.getInjectedObject(UserDao.class);
    
    final Task task1 = new Task("aaa", Category.IDEA, true, null);
    final Task task2 = new Task("bbb", Category.TODO, false, null);
    
    final List<Task> tasks = new ArrayList<Task>();
    tasks.add(task1);
    tasks.add(task2);
    
    EasyMock.expect(taskDao.findForUser(eq(user))).andReturn(tasks);
    taskDao.delete(task1);
    taskDao.delete(task2);
    EasyMock.expectLastCall();
    
    EasyMock.expect(userDao.load(eq(user.getId()))).andReturn(user);
    userDao.delete(user);
    EasyMock.expectLastCall();
    
    mockProvider.replayAll();
    
    userService.removeUser(user);
    
    mockProvider.verifyAll();
  }

}
