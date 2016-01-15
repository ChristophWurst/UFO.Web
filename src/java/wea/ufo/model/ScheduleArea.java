package wea.ufo.model;

import java.util.List;
import wea.ufo.ws.Area;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleArea {

	private final Area area;
	private List<ScheduleVenue> venues;

	public ScheduleArea(Area a) {
		area = a;
	}

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public String getName() {
		return area.getName();
	}

	/**
	 * Get the value of venues
	 *
	 * @return the value of venues
	 */
	public List<ScheduleVenue> getVenues() {
		return venues;
	}

	/**
	 * Set the value of venues
	 *
	 * @param venues new value of venues
	 */
	public void setVenues(List<ScheduleVenue> venues) {
		this.venues = venues;
	}


}
