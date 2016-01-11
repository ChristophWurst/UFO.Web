package wea.ufo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import wea.ufo.model.Area;
import wea.ufo.model.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class DummyBusinessDelegate implements UFOBusinessDelegate {

	private final List<Area> areas;
	private final List<Venue> venues;

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

		for (Area a : areas) {
			for (int i = 0; i < 20; i++) {
				Venue v = new Venue();
				v.setAreaId(a.getId());
				v.setId(i);
				v.setName("V" + i);
				venues.add(v);
			}
		}
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
	public boolean Login(String email, String password) {
		if (email.equals("user@example.com") && password.equals("12345")) {
			// Login successful
			return true;
		}
		return false;
	}

}
