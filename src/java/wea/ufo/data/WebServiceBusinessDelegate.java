package wea.ufo.data;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.*;
import wea.ufo.ws.UFO;
import wea.ufo.ws.UFOSoap;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at
 */
public class WebServiceBusinessDelegate implements UFOBusinessDelegate, Serializable {

	private static final Logger LOG = Logger.getLogger(WebServiceBusinessDelegate.class.getName());
	private UFOSoap ws = null;

	public WebServiceBusinessDelegate() {
		LOG.log(Level.INFO, "creating webServiceBusinessDelegate");

		UFO ufo;
		try {
			String wsdlLocation = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("WSDL_LOCATION");
			LOG.log(Level.INFO, "wsdl location: {0}", wsdlLocation == null ? "-" : wsdlLocation);
			ufo = new UFO(new URL(wsdlLocation));
		} catch (Exception ex) {
			LOG.log(Level.WARNING, "{0}could not load wsdl location from web.xml: ", ex.getClass().getName());
			ufo = new UFO();
		}
		ws = ufo.getUFOSoap();
	}

	@Override
	public List<Area> getAreas() {
		return ws.getAreas().getArea();
	}

	@Override
	public List<Venue> getVenues() {
		return ws.getVenues().getVenue();
	}

	@Override
	public List<Venue> getVenuesForArea(Area a) {
		return ws.getVenuesForArea(a).getVenue();
	}

	@Override
	public List<Category> getCategories() {
		return ws.getCategories().getCategory();
	}

	@Override
	public List<Artist> getArtists() {
		return ws.getArtists().getArtist();
	}

	@Override
	public Artist getArtistById(int artistId) {
		Artist search = new Artist();
		search.setId(artistId);
		return ws.getArtistById(search);
	}

	@Override
	public List<Artist> getArtistsForCategory(Category cat) {
		return ws.getArtistsForCategory(cat).getArtist();
	}

	@Override
	public List<TimeSlot> getTimeSlots() {
		return ws.getTimeSlots().getTimeSlot();
	}

	@Override
	public List<Spectacleday> getSpectacleDays() {
		return ws.getSpectacleDays().getSpectacleday();
	}

	@Override
	public List<Venue> getVenuesForPerformances(List<Performance> performances) {
		ArrayOfPerformance data = new ArrayOfPerformance();
		data.getPerformance().addAll(performances);
		return ws.getVenuesForPerformances(data).getVenue();
	}

	@Override
	public Performance getPerformance(int id) {
		return ws.getPerformance(id);
	}

	@Override
	public void updatePerformance(Spectacleday day, Performance performance) {
		ArrayOfPerformance data = new ArrayOfPerformance();
		data.getPerformance().add(performance);
		ws.updatePerformances(day, data);
	}

	@Override
	public List<Performance> getPerformancesForSpectacleDay(Spectacleday sd) {
		return ws.getPerformanesForSpetacleDay(sd).getPerformance();
	}

	@Override
	public List<SpectacledayTimeSlot> getSpectacleDayTimeSlotsForPerformance(Performance performance) {
		ArrayOfPerformance data = new ArrayOfPerformance();
		data.getPerformance().add(performance);
		return ws.getSpectacledayTimeSlotsForPerformances(data).getSpectacledayTimeSlot();
	}

	@Override
	public List<SpectacledayTimeSlot> getSpectacleDayTimeSlotsForSpectacleDay(Spectacleday day) {
		return ws.getSpectacleDayTimeSlotsForSpectacleDay(day).getSpectacledayTimeSlot();
	}

	@Override
	public boolean Login(String email, String password) {
		return ws.login(email, password);
	}

	@Override
	public List<Country> getCountries() {
		return ws.getCountries().getCountry();
	}

}
