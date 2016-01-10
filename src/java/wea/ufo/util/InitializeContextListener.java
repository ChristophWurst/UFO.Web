package wea.ufo.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContext;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class InitializeContextListener implements ServletContextListener {
    
    private static final Logger logger = Logger.getAnonymousLogger();
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.log(Level.INFO, "Context initialized");
        
        ServletContext cxt = sce.getServletContext();
        String bdc = cxt.getInitParameter("BUSINESS_DELEGATE");
        ServiceLocator.getInstance().init(bdc);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.log(Level.INFO, "Context destroyed");
    }
    
}
