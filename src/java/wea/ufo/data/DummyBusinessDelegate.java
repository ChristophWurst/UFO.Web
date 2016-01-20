package wea.ufo.data;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import wea.ufo.ws.*;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public class DummyBusinessDelegate implements UFOBusinessDelegate, Serializable {

	private final Random rand = new Random();
	private final List<Area> areas;
	private final List<Venue> venues;
	private final List<Category> categories;
	private final List<Artist> artists;
	private final List<TimeSlot> timeSlots;
	private final List<Spectacleday> spectacleDays;
	private final List<SpectacledayTimeSlot> spectacleDayTimeSlots;

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
			for (int i = 0; i < 8; i++) {
				Venue v = new Venue();
				v.setAreaId(a.getId());
				v.setId(a.getId() * 100 + i);
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
		categories.stream().forEach((c) -> {
			for (int j = 0; j < 11; j++) {
				Artist a = new Artist();
				a.setId(c.getId() * 100 + j);
				a.setCategoryId(c.getId());
				a.setName("Artist " + rand.nextDouble());
				a.setImage("images/" + (j + 1) + ".jpg");
				a.setVideo("dQw4w9WgXcQ");
				artists.add(a);
			}
		});

		timeSlots = new ArrayList<>();
		for (int tst = 15; tst <= 23; tst++) {
			TimeSlot ts = new TimeSlot();
			ts.setId(tst);
			ts.setStart(tst);
			ts.setEnd(tst + 1);
			timeSlots.add(ts);
		}

		spectacleDays = new ArrayList();
		for (int z = 0; z < 4; z++) {
			Spectacleday sd = new Spectacleday();
			ZonedDateTime zdt = ZonedDateTime.now();
			GregorianCalendar gc = GregorianCalendar.from(zdt);
			sd.setDay(new XMLGregorianCalendarImpl(gc));
			sd.setId(z);
			spectacleDays.add(sd);
		}

		spectacleDayTimeSlots = new ArrayList<>();
		spectacleDays.forEach((Spectacleday sd) -> {
			//Logger.getAnonymousLogger().log(Level.INFO, "{0} timeslots", timeSlots.size());
			timeSlots.forEach((TimeSlot ts) -> {
				SpectacledayTimeSlot sts = new SpectacledayTimeSlot();
				sts.setId(spectacleDayTimeSlots.size());
				sts.setTimeSlotId(ts.getId());
				sts.setSpectacledayId(sd.getId());
				sts.setTimeSlot(ts);
				spectacleDayTimeSlots.add(sts);
			});
		});
	}

	@Override
	public List<Area> getAreas() {
		return areas;
	}

	@Override
	public List<Venue> getVenues() {
		return venues;
	}

	@Override
	public List<Venue> getVenuesForArea(Area a) {
		return venues.stream().filter(v -> v.getAreaId() == a.getId()).collect(Collectors.toList());
	}

	@Override
	public List<Venue> getVenuesForPerformances(List<Performance> performances) {
		List<Venue> result = new ArrayList<>();
		performances.stream().forEach((p) -> {
			result.add(venues.stream().filter((Venue v) -> v.getId() == p.getVenueId()).findFirst().get());
		});
		return result;
	}

	@Override
	public List<Category> getCategories() {
		return categories;
	}

	@Override
	public List<Artist> getArtists() {
		return artists;
	}

	@Override
	public List<Artist> getArtistsForCategory(Category cat) {
		return artists.stream().filter(a -> a.getCategoryId() == cat.getId()).collect(Collectors.toList());
	}

	@Override
	public List<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	@Override
	public List<Spectacleday> getSpectacleDays() {
		return spectacleDays;
	}

	private Artist getRandomArtist() {
		int maxId = artists.size();
		int range = (int) (maxId * 1.4);
		int idx = rand.nextInt(range);
		if (idx > (maxId - 1)) {
			return null;
		}
		return artists.get(idx);
	}

	@Override
	public Performance getPerformance(int id) {
		Performance p = new Performance();
		p.setId(id);
		p.setArtistId(artists.get(rand.nextInt(artists.size()) - 1).getId());
		p.setSpectacledayTimeSlot(spectacleDayTimeSlots.get(rand.nextInt(spectacleDayTimeSlots.size())).getId());
		p.setVenueId(venues.get(rand.nextInt(venues.size())).getId());
		return p;
	}

	@Override
	public void updatePerformance(Spectacleday day, Performance performance) {
		// Nothing to save
	}

	@Override
	public List<Performance> getPerformancesForSpectacleDay(Spectacleday sd) {
		List<Performance> performances = new ArrayList();
		venues.forEach((Venue v) -> {
			List<SpectacledayTimeSlot> timeslots = getSpectacleDayTimeSlotsForSpectacleDay(sd);
			timeslots.stream().forEach((SpectacledayTimeSlot ts) -> {
				Artist a = getRandomArtist();
				if (a != null) {
					Performance p = new Performance();
					p.setSpectacledayTimeSlot(ts.getId());
					p.setVenueId(v.getId());
					p.setArtistId(a.getId());
					performances.add(p);
				}
			});
		});
		return performances;
	}

	@Override
	public List<SpectacledayTimeSlot> getSpectacleDayTimeSlotsForPerformance(Performance performance) {
		return spectacleDayTimeSlots;
	}

	@Override
	public List<SpectacledayTimeSlot> getSpectacleDayTimeSlotsForSpectacleDay(Spectacleday day) {
		return spectacleDayTimeSlots.stream().filter((sts) -> {
			return sts.getSpectacledayId() == day.getId();
		}).collect(Collectors.toList());
	}

	@Override
	public boolean Login(String email, String password) {
		// Login successful
		return email.equals("user@example.com") && password.equals("12345");
	}

}
