package wea.ufo.data;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.dashboard.Dashboard;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@ManagedBean(name="scheduleDashboard")
@ViewScoped
public class ScheduleDashboardBacker implements Serializable {
	
	private final static Logger logger = Logger.getAnonymousLogger();
	private Dashboard dashboard;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Application app = fc.getApplication();
		
		logger.log(Level.INFO, "scheduleDashboard initialized");
	}
	
	public Dashboard getDashboard() {
		return dashboard;
	}
	
}
