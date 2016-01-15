package wea.ufo.model;

import wea.ufo.ws.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleVenue {

	private final Venue venue;

	public ScheduleVenue(Venue v) {
		venue = v;
	}

	public String getName() {
		return venue.getDescription();
	}

}
