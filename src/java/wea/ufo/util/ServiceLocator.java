package wea.ufo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wea.ufo.data.UFOBusinessDelegate;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ServiceLocator {

    private static final Logger logger = Logger.getAnonymousLogger();
    private String businessDelegateClass;

    private ServiceLocator() {
        logger.log(Level.INFO, "ServiceLocator created");
    }

    public void init(String bdClass) {
        businessDelegateClass = bdClass;
    }

    public UFOBusinessDelegate getUFOBusinessDelegate() {
        Class<UFOBusinessDelegate> cls;
        try {
            cls = (Class<UFOBusinessDelegate>) Class.forName(businessDelegateClass);
            Constructor[] c = cls.getConstructors();
            Object[] arguments = {};
            UFOBusinessDelegate d = (UFOBusinessDelegate) c[0].newInstance(arguments);
            return d;
        } catch (ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    public static ServiceLocator getInstance() {
        return ServiceLocatorHolder.INSTANCE;
    }

    private static class ServiceLocatorHolder {

        private static final ServiceLocator INSTANCE = new ServiceLocator();
    }
}
