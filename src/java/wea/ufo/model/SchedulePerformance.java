package wea.ufo.model;

import java.io.Serializable;
import wea.ufo.ws.Artist;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class SchedulePerformance implements Serializable {

	private final Artist artist;

	public SchedulePerformance(Artist a) {
		artist = a;
	}

	public String getLabel() {
		if (artist == null) {
			return "-";
		}
		return "P " + artist.getName();
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
	
}
