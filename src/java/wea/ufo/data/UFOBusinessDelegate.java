package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import wea.ufo.ws.*;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public interface UFOBusinessDelegate extends Serializable {

	public List<Area> getAreas();

	public List<Venue> getVenuesForArea(Area a);

	public List<Category> getCategories();

	public List<Artist> getArtists();

	public List<Artist> getArtistsForCategory(Category cat);

	public List<TimeSlot> getTimeSlots();

	public List<Spectacleday> getSpectacleDays();

	public List<Performance> getPerformancesForSpectacleDay(Spectacleday sd);

	public List<SpectacledayTimeSlot> getSpectacleDayTimeSlotsForSpectacleDay(Spectacleday day);

	public boolean Login(String email, String password);

}
