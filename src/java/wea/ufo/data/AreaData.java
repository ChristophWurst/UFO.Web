package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import wea.ufo.ws.*;
import wea.ufo.util.ServiceLocator;

/**
 * @author Christoph Wurst
 */
@ViewScoped
@Named("areaData")
public class AreaData implements Serializable {

	private static final Logger LOG = Logger.getLogger(AreaData.class.getName());
	private List<Area> areas;
	private Area selectedArea;
	
	@Inject
	transient private ServiceLocator serviceLocator;

	/**
	 * Set the value of serviceLocator
	 *
	 * @param serviceLocator new value of serviceLocator
	 */
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public AreaData() {
		LOG.log(Level.INFO, "AreaData created");
	}

	@PostConstruct
	public void init() {
		areas = serviceLocator.getUFOBusinessDelegate().getAreas();
		selectedArea = areas.isEmpty() ? null : areas.get(0);
	}

	public Area getSelectedArea() {
		return selectedArea;
	}

	public void setSelectedArea(Area a) {
		selectedArea = a;
	}

	public void onAreaSelected(SelectEvent e) {
		Area selected = (Area) e.getObject();
		setSelectedArea(selected);
		LOG.log(Level.INFO, "Area selected: {0}", getSelectedArea().getName());
	}

	public List<Area> getAreas() {
		return areas;
	}

}
