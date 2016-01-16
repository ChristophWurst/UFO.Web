package wea.ufo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import wea.ufo.ws.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleVenue implements Serializable {

	private final Venue venue;
	private List<SchedulePerformance> performances;

	public ScheduleVenue(Venue v) {
		venue = v;
		performances = new ArrayList<>();
	}

	public Venue getVenue() {
		return venue;
	}

	public String getName() {
		return venue.getDescription();
	}

	/**
	 * Get the value of performances
	 *
	 * @return the value of performances
	 */
	public List<SchedulePerformance> getPerformances() {
		return performances;
	}

	/**
	 * Set the value of performances
	 *
	 * @param performances new value of performances
	 */
	public void setPerformances(List<SchedulePerformance> performances) {
		this.performances = performances;
	}

}
