package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.*;
import wea.ufo.ws.UFO;
import wea.ufo.ws.UFOSoap;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at
 */
public class WebServiceBusinessDelegate implements UFOBusinessDelegate, Serializable {

	private final UFOSoap ws;

	public WebServiceBusinessDelegate() {
		UFO ufo = new UFO();
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

}
