package wea.ufo.model;

import java.io.Serializable;
import wea.ufo.ws.Artist;
import wea.ufo.ws.Performance;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class SchedulePerformance implements Serializable {

	private final Artist artist;
	private final Performance performance;

	public SchedulePerformance(Artist a, Performance p) {
		artist = a;
		performance = p;
	}

	public String getLabel() {
		if (artist == null) {
			return "-";
		}
		return artist.getName();
	}

	public String getLinkToArtist() {
		if (artist == null) {
			return null;
		}
		return "artists?categoryId="
			+ artist.getCategoryId()
			+ "&artistId="
			+ artist.getId();
	}

	public String getLinkToPerformance() {
		if (performance == null) {
			return null;
		}
		return "performances"
			+ "?performanceId=" + performance.getId();
	}

}
