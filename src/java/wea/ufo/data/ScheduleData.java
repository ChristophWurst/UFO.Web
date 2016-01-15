package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.inject.Named;
import wea.ufo.model.ScheduleArea;
import wea.ufo.model.ScheduleSpectacleDay;
import wea.ufo.model.ScheduleTimeSlot;
import wea.ufo.model.ScheduleVenue;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Area;
import wea.ufo.ws.Spectacleday;
import wea.ufo.ws.TimeSlot;
import wea.ufo.ws.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@RequestScoped
public class ScheduleData implements Serializable {

	private static final Logger LOG = Logger.getLogger(ScheduleData.class.getName());
	private Application app;
	private List<ScheduleSpectacleDay> spectacleDays;
	private List<ScheduleArea> areas;
	private List<ScheduleTimeSlot> timeSlots;

	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "initializing scheduleData");
		loadData();
	}

	private void loadData() {
		LOG.log(Level.INFO, "loading schedule data");
		UFOBusinessDelegate bd = ServiceLocator.getInstance().getUFOBusinessDelegate();

		spectacleDays = new ArrayList<>();
		List<Spectacleday> days = bd.getSpectacleDays();
		days.stream().map((Spectacleday d) -> {
			return new ScheduleSpectacleDay(d);
		}).forEach(ssd -> {
			spectacleDays.add(ssd);
		});

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

}
