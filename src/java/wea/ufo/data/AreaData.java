package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.event.SelectEvent;
import wea.ufo.ws.*;
import wea.ufo.util.ServiceLocator;

/**
 * @author Christoph Wurst
 */
@RequestScoped
@Named("areaData")
public class AreaData implements Serializable {

	private static final Logger LOG = Logger.getLogger(AreaData.class.getName());
	private List<Area> areas;
	private Area selectedArea;

	public AreaData() {
		LOG.log(Level.INFO, "AreaData created");
	}

	@PostConstruct
	public void init() {
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
		LOG.log(Level.INFO, "Area selected: {0}", getSelectedArea().getName());
	}

	public List<Area> getAreas() {
		return areas;
	}

}
