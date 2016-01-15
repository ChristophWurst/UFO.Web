package wea.ufo.model;

import wea.ufo.ws.TimeSlot;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleTimeSlot {

	private final TimeSlot timeSlot;

	public ScheduleTimeSlot(TimeSlot ts) {
		timeSlot = ts;
	}

	public String getLabel() {
		return timeSlot.getStart() + "-" + timeSlot.getEnd();
	}

}
