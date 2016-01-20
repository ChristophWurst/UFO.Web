package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Performance;
import wea.ufo.ws.Spectacleday;
import wea.ufo.ws.SpectacledayTimeSlot;
import wea.ufo.ws.TimeSlot;
import wea.ufo.ws.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@ViewScoped
public class PerformanceData implements Serializable {

	private static final Logger LOG = Logger.getLogger(PerformanceData.class.getName());
	private Performance performance;
	private List<Venue> venues;
	private Spectacleday day;
	private List<SpectacledayTimeSlot> spectacledayTimeSlots;
	private SpectacledayTimeSlot selectedSpectacledayTimeSlot;

	private Venue selectedVenue;

	@Inject
	transient private ServiceLocator serviceLocator;

	@Inject
	private HttpServletRequest httpServletRequest;
	private Spectacleday spectacleDay;

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	/**
	 * Get the value of venues
	 *
	 * @return the value of venues
	 */
	public List<Venue> getVenues() {
		loadData();
		return venues.stream().sorted((Venue v1, Venue v2) -> v1.getDescription().compareTo(v2.getDescription())).collect(Collectors.toList());
	}

	/**
	 * Set the value of venues
	 *
	 * @param venues new value of venues
	 */
	public void setVenues(List<Venue> venues) {
		this.venues = venues;
	}

	/**
	 * Get the value of spectacledayTimeslots
	 *
	 * @return the value of spectacledayTimeslots
	 */
	public List<SpectacledayTimeSlot> getSpectacledayTimeslots() {
		return spectacledayTimeSlots;
	}

	/**
	 * Set the value of spectacledayTimeslots
	 *
	 * @param spectacledayTimeSlot new value of spectacledayTimeSlot
	 */
	public void setSpectacledayTimeslots(List<SpectacledayTimeSlot> spectacledayTimeSlot) {
		this.spectacledayTimeSlots = spectacledayTimeSlot;
	}

	/**
	 * Get the value of selectedVenue
	 *
	 * @return the value of selectedVenue
	 */
	public Venue getSelectedVenue() {
		loadData();
		return selectedVenue;
	}

	/**
	 * Set the value of selectedVenue
	 *
	 * @param selectedVenue new value of selectedVenue
	 */
	public void setSelectedVenue(Venue selectedVenue) {
		LOG.log(Level.INFO, "performance venue <{0}> selected", selectedVenue.getDescription());
		this.selectedVenue = selectedVenue;
	}

	/**
	 * Get the value of performance
	 *
	 * @return the value of performance
	 */
	public Performance getPerformance() {
		loadData();
		return performance;
	}

	/**
	 * Set the value of performance2
	 *
	 * @param performance new value of performance
	 */
	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	/**
	 * Get the value of day
	 *
	 * @return the value of day
	 */
	public Spectacleday getDay() {
		return day;
	}

	/**
	 * Set the value of day
	 *
	 * @param day new value of day
	 */
	public void setDay(Spectacleday day) {
		this.day = day;
	}

	/**
	 * Get the value of selectedSpectacledayTimeSlot
	 *
	 * @return the value of selectedSpectacledayTimeSlot
	 */
	public SpectacledayTimeSlot getSelectedSpectacledayTimeSlot() {
		return selectedSpectacledayTimeSlot;
	}

	/**
	 * Set the value of selectedSpectacledayTimeSlot
	 *
	 * @param selectedSpectacledayTimeSlot new value of
	 * selectedSpectacledayTimeSlot
	 */
	public void setSelectedSpectacledayTimeSlot(SpectacledayTimeSlot selectedSpectacledayTimeSlot) {
		this.selectedSpectacledayTimeSlot = selectedSpectacledayTimeSlot;
	}
	
	public void onVenueChanged(AjaxBehaviorEvent e) {
		//Venue v = (Venue) e.getNewValue();
		LOG.log(Level.INFO, "performance venue <{0}> selected");
	}

	/**
	 * @return
	 */
	public String saveChanges() {
		LOG.log(Level.INFO, "saving changes of performance <{0}>", performance.getId());
		
		performance.setVenueId(getSelectedVenue().getId());
		performance.setSpectacledayTimeSlot(getSelectedSpectacledayTimeSlot().getId());
		try {
			serviceLocator.getUFOBusinessDelegate().updatePerformance(spectacleDay, performance);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "error updating performance: {0}", e.getMessage());
			return "performances?performanceId=" + performance.getId() + "&faces-redirect=true";
		}
		return "schedule";
	}

	private void loadData() {
		if (performance == null) {
			String param = httpServletRequest.getParameter("performanceId");
			if (param == null) {
				return;
			}

			int performanceId = Integer.parseInt(param);
			performance = serviceLocator.getUFOBusinessDelegate().getPerformance(performanceId);

			venues = serviceLocator.getUFOBusinessDelegate().getVenues();
			setSelectedVenue(venues.stream().filter((Venue v) -> v.getId() == performance.getVenueId()).findFirst().get());

			List<SpectacledayTimeSlot> timeSlotsForPerformance = serviceLocator.getUFOBusinessDelegate().getSpectacleDayTimeSlotsForPerformance(performance);
			SpectacledayTimeSlot performaneTimeslot = timeSlotsForPerformance.get(0); // there is only one

			spectacleDay = new Spectacleday();
			spectacleDay.setId(performaneTimeslot.getSpectacledayId());
			spectacledayTimeSlots = serviceLocator.getUFOBusinessDelegate().getSpectacleDayTimeSlotsForSpectacleDay(spectacleDay);
			// Inject timeslots (needed for displaying the start/end time
			List<TimeSlot> timeSlots = serviceLocator.getUFOBusinessDelegate().getTimeSlots();
			spectacledayTimeSlots.forEach((SpectacledayTimeSlot sts) -> {
				sts.setTimeSlot(timeSlots.stream().filter((TimeSlot ts) -> {
					return ts.getId() == sts.getTimeSlotId();
				}).findFirst().get());
			});

			// Get timeslots on that very same day
			setSelectedSpectacledayTimeSlot(spectacledayTimeSlots.stream().filter((SpectacledayTimeSlot sts) -> {
				return sts.getId() == performaneTimeslot.getId();
			}).findFirst().get());
		}
	}

}
