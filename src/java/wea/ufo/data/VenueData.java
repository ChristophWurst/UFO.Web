/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import wea.ufo.model.Area;
import wea.ufo.model.Venue;
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
