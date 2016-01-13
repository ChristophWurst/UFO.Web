package wea.ufo.data;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@RequestScoped
@Named("scheduleData")
public class ScheduleData implements Serializable {

	private static final Logger logger = Logger.getAnonymousLogger();
	private Application app;
	private DashboardModel model;
	private Dashboard dashboard;

	/**
	 * Get the value of model
	 *
	 * @return the value of model
	 */
	public DashboardModel getModel() {
		return model;
	}

	/**
	 * Set the value of model
	 *
	 * @param model new value of model
	 */
	public void setModel(DashboardModel model) {
		this.model = model;
	}

	@PostConstruct
	public void init() {
		logger.log(Level.INFO, "initializing scheduleData");

		model = new DefaultDashboardModel();

		for (int i = 1; i <= 7; i++) {
			DefaultDashboardColumn col = new DefaultDashboardColumn();
			col.addWidget("p" + i + "-" + 1);
			col.addWidget("p" + i + "-" + 2);
			col.addWidget("p" + i + "-" + 3);
			model.addColumn(col);
		}

		FacesContext fc = FacesContext.getCurrentInstance();
		app = fc.getApplication();
		dashboard = (Dashboard) app.createComponent(fc, "org.primefaces.component.Dashboard", "org.primefaces.component.DashboardRenderer");
		dashboard.setId("schedule");
		dashboard.setModel(getModel());

		loadData();
	}

	private void loadData() {
		logger.log(Level.INFO, "loading schedule data");

		dashboard.getChildren().clear();
		FacesContext fc = FacesContext.getCurrentInstance();

		for (int i = 1; i <= 7; i++) {
			for (int y = 1; y <= 3; y++) {
				Panel pan = (Panel) app.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
				pan.setId("p" + i + "-" + y);
				pan.setHeader("p" + i + "-" + y);
				pan.setClosable(true);
				dashboard.getChildren().add(pan);
			}
		}
	}

	/**
	 * Get the value of dashboard
	 *
	 * @return the value of dashboard
	 */
	public Dashboard getDashboard() {
		logger.log(Level.INFO, "get db");
		return dashboard;
	}

	/**
	 * Set the value of dashboard
	 *
	 * @param dashboard new value of dashboard
	 */
	public void setDashboard(Dashboard dashboard) {
		logger.log(Level.INFO, "set db");
		this.dashboard = dashboard;
		loadData();
	}

}
