package m1.jee.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EMF implements ServletContextListener {
  private static EntityManagerFactory emf;
  final private static String PU = "jee-projectPU";

  /**
   * Create entity manager factory
   * @param event ServletContextEvent
   */
  @Override
  public void contextInitialized(ServletContextEvent event) {
    emf = Persistence.createEntityManagerFactory(PU);
  }

  /**
   * Close entity manager when context is destroyed
   * @param event ServletContextEvent
   */
  @Override
  public void contextDestroyed(ServletContextEvent event) {
    emf.close();
  }

  /**
   * Create entity manager
   * @return EntityManager
   */
  public static EntityManager createEntityManager() {
    if (emf == null) {
      throw new IllegalStateException("Context is not initialized yet.");
    }

    return emf.createEntityManager();
  }
}