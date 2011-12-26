package com.acme.youTask.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.acme.youTask.business.TaskService;
import com.acme.youTask.business.TaskServiceBean;
import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.dao.TaskDaoBean;
import com.acme.youTask.dao.common.GenericDao;
import com.acme.youTask.dao.common.JpaGenericDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.enums.Category;

/**
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
public final class ArquillianHelper {

    private ArquillianHelper(){}

    public static final WebArchive aufgabeWebArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Task.class.getPackage())
                .addPackage(Category.class.getPackage())
                .addClasses(TaskServiceBean.class, TaskService.class).addClasses(TaskDaoBean.class)
                .addClasses(JpaGenericDao.class, GenericDao.class, TaskDao.class, TaskDaoBean.class)
                .addAsLibraries(MavenArtifactResolver.resolve("org.jboss.seam.solder:seam-solder:3.0.0.Final"))
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");


}
