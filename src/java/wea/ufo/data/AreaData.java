package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.event.SelectEvent;
import wea.ufo.ws.*;
import wea.ufo.util.ServiceLocator;

/**
 * @author Christoph Wurst
 */
@ManagedBean(name = "areaData")
@RequestScoped
public class AreaData implements Serializable {

	private static final Logger logger = Logger.getAnonymousLogger();
	private final List<Area> areas;
	private Area selectedArea;

	public AreaData() {
		logger.log(Level.INFO, "AreaData created");

		areas = ServiceLocator.getInstance().getUFOBusinessDelegate().getAreas();

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
		logger.log(Level.INFO, "Area selected: {0}", getSelectedArea().getName());
	}

	public List<Area> getAreas() {
		return areas;
	}

}
