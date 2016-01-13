package wea.ufo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import wea.ufo.ws.*;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class DummyBusinessDelegate implements UFOBusinessDelegate {

	private final List<Area> areas;
	private final List<Venue> venues;
	private final List<Category> categories;
	private final List<Artist> artists;

	public DummyBusinessDelegate() {
		areas = new ArrayList<>();
		venues = new ArrayList<>();

		Area a1 = new Area();
		a1.setId(1);
		a1.setName("Hauptplatz");
		areas.add(a1);
		Area a2 = new Area();
		a2.setId(2);
		a2.setName("Rathausplatz");
		areas.add(a2);

		areas.stream().forEach((Area a) -> {
			for (int i = 0; i < 20; i++) {
				Venue v = new Venue();
				v.setAreaId(a.getId());
				v.setId(i);
				v.setDescription("Venue " + i);
				v.setShortDescription("V" + i);
				v.setLatitude(48.2950437);
				v.setLongitude(14.2573657);
				venues.add(v);
			}
		});

		categories = new ArrayList<>();
		String[] cats = {"Tanz", "Musik", "Sonstiges"};
		int i = 0;
		for (String cat : cats) {
			Category c = new Category();
			c.setId(i++);
			c.setDescription(cat);
			categories.add(c);
		}
		
		artists = new ArrayList<>();
		Random rand = new Random();
		categories.stream().forEach((c) -> {
			for (int j = 0; j < 11; j++) {
				Artist a = new Artist();
				a.setId(j * j);
				a.setCategoryId(c.getId());
				a.setName("Artist " + rand.nextDouble());
				a.setImage("images/" + (j + 1) + ".jpg");
				artists.add(a);
			}
		});
	}

	@Override
	public List<Area> getAreas() {
		return areas;
	}

	@Override
	public List<Venue> getVenuesForArea(Area a) {
		return venues.stream().filter(v -> v.getAreaId() == a.getId()).collect(Collectors.toList());
	}

	@Override
	public List<Category> getCategories() {
		return categories;
	}

	@Override
	public List<Artist> getArtistsForCategory(Category cat) {
		return artists.stream().filter(a -> a.getCategoryId() == cat.getId()).collect(Collectors.toList());
	}

	@Override
	public boolean Login(String email, String password) {
		if (email.equals("user@example.com") && password.equals("12345")) {
			// Login successful
			return true;
		}
		return false;
	}

}
