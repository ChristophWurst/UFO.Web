package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.TabChangeEvent;
import wea.ufo.model.ScheduleArea;
import wea.ufo.model.SchedulePerformance;
import wea.ufo.model.ScheduleSpectacleDay;
import wea.ufo.model.ScheduleTimeSlot;
import wea.ufo.model.ScheduleVenue;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Area;
import wea.ufo.ws.Artist;
import wea.ufo.ws.Performance;
import wea.ufo.ws.Spectacleday;
import wea.ufo.ws.SpectacledayTimeSlot;
import wea.ufo.ws.TimeSlot;
import wea.ufo.ws.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@ViewScoped
public class ScheduleData implements Serializable {

	private static final Logger LOG = Logger.getLogger(ScheduleData.class.getName());
	private Application app;
	private ScheduleSpectacleDay selectedSpectacleDay;
	private int selectedSpectacleDayIndex = 0;
	private List<ScheduleSpectacleDay> spectacleDays;
	private List<ScheduleArea> areas;
	private List<ScheduleTimeSlot> timeSlots;
	private List<SpectacledayTimeSlot> spectacleDayTimeSlots;
	private List<Artist> artists;
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

	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "initializing scheduleData");
		loadData();
		if (!spectacleDays.isEmpty()) {
			loadSelectedSpectacleDayData();
		}
	}

	private void loadData() {
		LOG.log(Level.INFO, "loading schedule data");
		UFOBusinessDelegate bd = serviceLocator.getUFOBusinessDelegate();

		spectacleDays = new ArrayList<>();
		List<Spectacleday> days = bd.getSpectacleDays();
		days.stream().map((Spectacleday d) -> {
			return new ScheduleSpectacleDay(d);
		}).forEach(ssd -> {
			spectacleDays.add(ssd);
		});
		if (!spectacleDays.isEmpty()) {
			selectedSpectacleDay = spectacleDays.get(0);
			selectedSpectacleDayIndex = 0;
		}

		areas = new ArrayList<>();
		List<Area> wsAreas = bd.getAreas();
		wsAreas.stream().map((a) -> {
			ScheduleArea sa = new ScheduleArea(a);
			List<Venue> wsVenues = bd.getVenuesForArea(a);
			List<ScheduleVenue> venues = new ArrayList<>();
			wsVenues.stream().forEach((v) -> {
				venues.add(new ScheduleVenue(v));
			});
			sa.setVenues(venues);
			return sa;
		}).forEach((sa) -> {
			areas.add(sa);
		});

		timeSlots = new ArrayList<>();
		List<TimeSlot> ts = bd.getTimeSlots();
		ts.stream().forEach((t) -> {
			timeSlots.add(new ScheduleTimeSlot(t));
		});
		
		artists = bd.getArtists();
	}

	private void loadSelectedSpectacleDayData() {
		LOG.log(Level.INFO, "loading spectacleday data");

		UFOBusinessDelegate bd = serviceLocator.getUFOBusinessDelegate();
		spectacleDayTimeSlots = new ArrayList<>();
		spectacleDayTimeSlots = bd.getSpectacleDayTimeSlotsForSpectacleDay(spectacleDays.get(selectedSpectacleDayIndex).getSpectacleDay());
		List<Performance> allPerformances = bd.getPerformancesForSpectacleDay(selectedSpectacleDay.getSpectacleDay());

		areas.forEach((ScheduleArea sa) -> {
			sa.getVenues().forEach((ScheduleVenue v) -> {
				List<SchedulePerformance> performances = new ArrayList<>();

				spectacleDayTimeSlots.forEach((SpectacledayTimeSlot sdts) -> {
					// get performance fro this venue/timeslot combination
					Performance per = null;
					Artist art = null;

					try {
						per = allPerformances.stream().filter((Performance p) -> {
							return p.getVenueId() == v.getVenue().getId()
								&& p.getSpectacledayTimeSlot() == sdts.getId();
						}).findFirst().get();
						
						int artistId = per.getArtistId();
						art = artists.stream().filter((Artist a) -> {
							return artistId == a.getId();
						}).findFirst().get();
					} catch (NoSuchElementException e) {
						// Nothing to do
					}

					performances.add(new SchedulePerformance(art, per));
				});
				v.setPerformances(performances);
			});
		});
	}

	/**
	 * Get the value of spectacleDays
	 *
	 * @return the value of spectacleDays
	 */
	public List<ScheduleSpectacleDay> getSpectacleDays() {
		return spectacleDays;
	}

	/**
	 * Get the value of areas
	 *
	 * @return the value of areas
	 */
	public List<ScheduleArea> getAreas() {
		return areas;
	}

	/**
	 * Set the value of areas
	 *
	 * @param areas new value of areas
	 */
	public void setAreas(List<ScheduleArea> areas) {
		this.areas = areas;
	}

	/**
	 * Get the value of timeSlots
	 *
	 * @return the value of timeSlots
	 */
	public List<ScheduleTimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public List<SpectacledayTimeSlot> getSpectacleDayTimeSlots() {
		return spectacleDayTimeSlots;
	}

	public int getSelectedSpectacleDayIndex() {
		return spectacleDays.indexOf(selectedSpectacleDay);
	}

	public void setSelectedSpectacleDayIndex(int idx) {
		// ignore for now, we get it in the event handler anyways
	}

	public void onSpectacleDaySelected(TabChangeEvent e) {
		ScheduleSpectacleDay ssd = (ScheduleSpectacleDay) e.getData();
		LOG.log(Level.INFO, "ssd {0} selected", ssd.getLabel());
	}

}
