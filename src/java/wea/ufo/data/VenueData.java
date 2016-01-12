package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.component.gmap.GMap;
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
@ManagedBean(name = "venueData")
@RequestScoped
public class VenueData implements Serializable {

	private static final Logger logger = Logger.getAnonymousLogger();
	private List<Venue> venues;
	private Area selectedArea;
	private Venue selectedVenue;
	private MapModel mapModel;

	@ManagedProperty("#{areaData}")
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
			logger.log(Level.INFO, "Settings venue mapModel to {0}, {1}", (Double.toString(selectedVenue.getLatitude())) + (Double.toString(selectedVenue.getLongitude())));
			Marker m = new Marker(new LatLng(selectedVenue.getLatitude(), selectedVenue.getLongitude()));
			mapModel.getMarkers().add(m);
		} else {
			mapModel = null;
		}
	}

	public MapModel getMapModel() {
		logger.log(Level.INFO, "now");
		return mapModel;
	}

	public void onVenueSelected(SelectEvent e) {
		Venue selected = (Venue) e.getObject();
		setSelectedVenue(selected);
		logger.log(Level.INFO, "Venue selected: {0}", getSelectedVenue().getShortDescription());
	}

	private void loadVenues(Area a) {
		logger.log(Level.INFO, "Loading venues for area {0}", areaData.getSelectedArea().getName());
		venues = ServiceLocator.getInstance().getUFOBusinessDelegate().getVenuesForArea(a);
	}

	public List<Venue> getVenues() {
		Area a = areaData.getSelectedArea();
		if (a != selectedArea) {
			selectedArea = a;
			loadVenues(a);
		}
		return venues;
	}
}
