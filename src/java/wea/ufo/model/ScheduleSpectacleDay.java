package wea.ufo.model;

import wea.ufo.ws.Spectacleday;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class ScheduleSpectacleDay {

	private final Spectacleday spectacleDay;

	public ScheduleSpectacleDay(Spectacleday sd) {
		spectacleDay = sd;
	}

	public String getLabel() {
		int day = spectacleDay.getDay().getDay();
		int month = spectacleDay.getDay().getMonth();
		int year = spectacleDay.getDay().getYear();
		return day + ". " + month + ". " + year;
	}

}
