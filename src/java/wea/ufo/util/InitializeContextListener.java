package wea.ufo.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContext;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class InitializeContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getAnonymousLogger();

	@Inject
	private ServiceLocator serviceLocator;

	/**
	 * Set the value of serviceLocator
	 *
	 * @param serviceLocator new value of serviceLocator
	 */
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.log(Level.INFO, "Context initialized");

		ServletContext cxt = sce.getServletContext();
		String bdc = cxt.getInitParameter("BUSINESS_DELEGATE");
		serviceLocator.init(bdc);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOG.log(Level.INFO, "Context destroyed");
	}

}
