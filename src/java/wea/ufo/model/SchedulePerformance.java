package wea.ufo.model;

import java.io.Serializable;
import wea.ufo.ws.Performance;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class SchedulePerformance implements Serializable {

	private final Performance performance;

	public SchedulePerformance(Performance p) {
		performance = p;
	}

	public String getLabel() {
		if (performance == null) {
			return "-";
		}
		return "P " + performance.getArtistId();
	}

}
