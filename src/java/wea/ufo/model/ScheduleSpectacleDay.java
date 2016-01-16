package wea.ufo.model;

import java.io.Serializable;
import wea.ufo.ws.Spectacleday;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleSpectacleDay implements Serializable {

	private final Spectacleday spectacleDay;

	public ScheduleSpectacleDay(Spectacleday sd) {
		spectacleDay = sd;
	}

	public Spectacleday getSpectacleDay() {
		return spectacleDay;
	}

	public String getLabel() {
		int day = spectacleDay.getDay().getDay();
		int month = spectacleDay.getDay().getMonth();
		int year = spectacleDay.getDay().getYear();
		return day + ". " + month + ". " + year;
	}

}
