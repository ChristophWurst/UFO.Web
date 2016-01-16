package wea.ufo.model;

import java.io.Serializable;
import wea.ufo.ws.TimeSlot;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleTimeSlot implements Serializable {

	private final TimeSlot timeSlot;

	public ScheduleTimeSlot(TimeSlot ts) {
		timeSlot = ts;
	}

	public String getLabel() {
		return timeSlot.getStart() + "-" + timeSlot.getEnd();
	}

}
