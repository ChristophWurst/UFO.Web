package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
	private HttpServletRequest httpServletRequest;

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

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public AreaData() {
		LOG.log(Level.INFO, "AreaData created");
	}

	@PostConstruct
	public void init() {
		areas = serviceLocator.getUFOBusinessDelegate().getAreas().stream().sorted((Area a1, Area a2) -> {
			return a1.getName().compareTo(a2.getName());
		}).collect(Collectors.toList());
		selectAreaFromParameter();
	}

	private void selectAreaFromParameter() {
		if (areas.isEmpty()) {
			// nothing to select
			setSelectedArea(null);
		}

		String parameter = httpServletRequest.getParameter("areaId");
		if (parameter != null) {
			final int parameterId = Integer.parseInt(parameter);

			// try to locate get-parameter area
			try {
				Area fromParameter = areas.stream().filter((Area a) -> {
					return a.getId() == parameterId;
				}).findFirst().get();
				setSelectedArea(fromParameter);
				LOG.log(Level.INFO, "selected area {0} based on get-parameter", fromParameter.getId());
			} catch (NoSuchElementException e) {
				// use default - nothing to do
				setSelectedArea(null);
				LOG.log(Level.WARNING, "could not select area {0} based on get-parameter", parameterId);
			}
		} else {
			setSelectedArea(areas.get(0));
		}
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
