package wea.ufo.data;

import java.util.List;
import javax.inject.Inject;
import wea.ufo.ws.*;
import wea.ufo.ws.UFO;
import wea.ufo.ws.UFOSoap;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at
 */
public class WebServiceBusinessDelegate implements UFOBusinessDelegate {

	private UFOSoap ws;

	public WebServiceBusinessDelegate() {
		UFO ufo = new UFO();
		ws = ufo.getUFOSoap();
	}
	
	@Override
	public List<Area> getAreas() {
		return ws.getAreas().getArea();
	}

	@Override
	public List<Venue> getVenuesForArea(Area a) {
		return ws.getVenuesForArea(a).getVenue();
	}

	@Override
	public List<Category> getCategories() {
		return ws.getCategories().getCategory();
	}

	@Override
	public List<Artist> getArtistsForCategory(Category cat) {
		return ws.getArtistsForCategory(cat).getArtist();
	}

	@Override
	public boolean Login(String email, String password) {
		return ws.login(email, password);
	}
	
}
