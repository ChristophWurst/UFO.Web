package wea.ufo.data;

import java.util.List;
import wea.ufo.ws.*;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public interface UFOBusinessDelegate {

	public List<Area> getAreas();

	public List<Venue> getVenuesForArea(Area a);

	public List<Category> getCategories();

	public List<Artist> getArtistsForCategory(Category cat);

	public boolean Login(String email, String password);

}
