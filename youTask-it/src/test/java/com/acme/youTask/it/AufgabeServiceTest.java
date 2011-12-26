package com.acme.youTask.it;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.youTask.business.TaskService;
import com.acme.youTask.dao.AbstractYouTaskTest;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;
import com.acme.youTask.util.ArquillianHelper;

import de.akquinet.jbosscc.needle.db.transaction.VoidRunnable;

/**
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@RunWith(Arquillian.class)
public class AufgabeServiceTest extends AbstractYouTaskTest {

  @Inject
  private Logger log;

  @Inject
  private TaskService aufgabeService;

  @Deployment
  public static WebArchive createWebArchive() {

    final WebArchive webArchive = ArquillianHelper.aufgabeWebArchive;
    return webArchive;
  }

  @Test
  public void testPersist() throws Exception {
    log.info("testPersist");

    // first create and save a user
    final User user = new User("xXx", "vin", "disel", "triple@xXx.com");
    transactionHelper.executeInTransaction(new VoidRunnable() {
      @Override
      public void doRun(final EntityManager entityManager) throws Exception {
        transactionHelper.persist(user);
      }
    });
    
    final Task aufgabe1 = new Task("call my wife", Category.TODO, false, user);
    final Task aufgabe2 = new Task("call my wife twice", Category.TODO, false, user);
    final Task aufgabe3 = new Task("rearrange office", Category.IDEA, false, user);

    aufgabeService.saveTask(aufgabe1);
    aufgabeService.saveTask(aufgabe2);
    aufgabeService.saveTask(aufgabe3);

    Assert.assertNotNull(aufgabe1.getId());

    Assert.assertEquals(3, aufgabeService.loadAll().size());
    Assert.assertEquals(2, aufgabeService.loadForCategory(Category.TODO).size());

    log.info("testPersist succeeded");
  }
}
