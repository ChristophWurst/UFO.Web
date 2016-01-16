package wea.ufo.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import wea.ufo.data.UFOBusinessDelegate;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Singleton
public class ServiceLocator implements Serializable {

	private static final Logger LOG = Logger.getAnonymousLogger();
	private String businessDelegateClass;

	public ServiceLocator() {
		LOG.log(Level.INFO, "ServiceLocator created");
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
			LOG.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}

}
