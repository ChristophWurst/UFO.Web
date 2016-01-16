package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import wea.ufo.ws.*;
import wea.ufo.util.ServiceLocator;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@ViewScoped
public class VenueData implements Serializable {

	private static final Logger LOG = Logger.getLogger(VenueData.class.getName());
	private List<Venue> venues;
	private Area selectedArea;
	private Venue selectedVenue;
	private MapModel mapModel;

	@Inject
	transient private ServiceLocator serviceLocator;

	@Inject
	private HttpServletRequest httpServletRequest;

	/**
	 * Set the value of serviceLocator
	 *
	 * @param serviceLocator new value of serviceLocator
	 */
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	/**
	 * Set the value of httpServletRequest
	 *
	 * @param request
	 */
	public void setHttpServletRequest(HttpServletRequest request) {
		httpServletRequest = request;
	}

	@Inject
	private AreaData areaData;

	public VenueData() {
		venues = new ArrayList<>();
	}

	public void setAreaData(AreaData ad) {
		areaData = ad;
	}

	/**
	 * Get the value of selectedVenue
	 *
	 * @return the value of selectedVenue
	 */
	public Venue getSelectedVenue() {
		loadVenues();
		return selectedVenue;
	}

	/**
	 * Set the value of selectedVenue
	 *
	 * @param selectedVenue new value of selectedVenue
	 */
	public void setSelectedVenue(Venue selectedVenue) {
		this.selectedVenue = selectedVenue;
		if (selectedVenue != null) {
			mapModel = new DefaultMapModel();
			LOG.log(Level.INFO, "Settings venue mapModel to {0}, {1}", (Double.toString(selectedVenue.getLatitude())) + (Double.toString(selectedVenue.getLongitude())));
			Marker m = new Marker(new LatLng(selectedVenue.getLatitude(), selectedVenue.getLongitude()));
			mapModel.getMarkers().add(m);
		} else {
			mapModel = null;
		}
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void onVenueSelected(SelectEvent e) {
		Venue selected = (Venue) e.getObject();
		setSelectedVenue(selected);
		LOG.log(Level.INFO, "Venue selected: {0}", getSelectedVenue().getShortDescription());
	}

	private void loadVenues() {
		LOG.log(Level.INFO, "Loading venues for area {0}", areaData.getSelectedArea().getName());
		Area a = areaData.getSelectedArea();
		if (a != selectedArea) {
			selectedArea = a;
			venues = serviceLocator.getUFOBusinessDelegate().getVenuesForArea(a);
			selectVenueFromParameter();
		}
	}

	private void selectVenueFromParameter() {
		if (venues.isEmpty()) {
			// nothing to select
			setSelectedVenue(null);
		}

		String parameter = httpServletRequest.getParameter("venueId");
		if (parameter != null) {
			final int parameterId = Integer.parseInt(parameter);

			// try to locate get-parameter area
			try {
				Venue fromParameter = venues.stream().filter((Venue v) -> {
					return v.getId() == parameterId;
				}).findFirst().get();
				setSelectedVenue(fromParameter);
				LOG.log(Level.INFO, "selected venue {0} based on get-parameter", fromParameter.getId());
			} catch (NoSuchElementException e) {
				// use default - nothing to do
				setSelectedVenue(null);
				LOG.log(Level.WARNING, "could not select venue {0} based on get-parameter", parameterId);
			}
		} else {
			setSelectedVenue(venues.get(0));
		}
	}

	public List<Venue> getVenues() {
		loadVenues();
		return venues;
	}

	public List<Venue> getVenuesForArea(Area a) {
		List<Venue> venue = new ArrayList<>();
		Venue ve = new Venue();
		ve.setDescription("Area a");
		venue.add(ve);
		ve = new Venue();
		ve.setDescription("Area b");
		venue.add(ve);
		return venue;
	}
}
