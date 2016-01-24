package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@ViewScoped
public class VenueSearch implements Serializable {

	private static final Logger LOG = Logger.getLogger(VenueSearch.class.getName());
	private List<Venue> venue;
	private Venue selectedVenue;

	@Inject
	transient private ServiceLocator serviceLocator;

	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "artistSearch constructed");
	}

	private void loadData() {
		if (venue == null) {
			venue = serviceLocator.getUFOBusinessDelegate().getVenues();
		}
	}

	public List<Venue> searchVenues(String query) {
		loadData();
		final String lowerQuery = query.toLowerCase();
		return venue.stream().filter((Venue v) -> {
			return v.getDescription().toLowerCase().contains(lowerQuery);
		}).collect(Collectors.toList());
	}

	public void onVenueSelected(SelectEvent e) {
		Venue selected = (Venue) e.getObject();
		setSelectedVenue(selected);
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
	 * @param selectedArtist new value of selectedVenue
	 */
	public void setSelectedVenue(Venue selectedArtist) {
		if (selectedArtist != null) {
			LOG.log(Level.INFO, "artist <{0}> selected in autocomplete", selectedArtist.getDescription());
		} else {
			LOG.log(Level.INFO, "no artist selected in autocomplete");
		}
		this.selectedVenue = selectedArtist;
	}

	public String getSelectedVenueLink() {
		Venue v = getSelectedVenue();
		if (v == null) {
			LOG.log(Level.WARNING, "no artist set for autocomplete");
			return "artists";
		}
		LOG.log(Level.INFO, "generating link for artist <{0}> ", v.getDescription());
		return "venues"
			+ "?areaId=" + v.getAreaId()
			+ "&venueId=" + v.getId();
	}

	public String getSelectedValue() {
		return getSelectedVenue() == null ? "" : getSelectedVenue().getDescription();
	}

}
