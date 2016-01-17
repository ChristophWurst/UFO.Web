package wea.ufo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.SelectEvent;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Artist;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@ViewScoped
public class ArtistSearch implements Serializable {

	private static final Logger LOG = Logger.getLogger(ArtistSearch.class.getName());
	private List<Artist> artists;
	private Artist selectedArtist;

	@Inject
	transient private ServiceLocator serviceLocator;

	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "artistSearch constructed");
	}

	private void loadData() {
		if (artists == null) {
			artists = serviceLocator.getUFOBusinessDelegate().getArtists();
		}
	}

	public List<Artist> searchArtists(String query) {
		loadData();
		final String lowerQuery = query.toLowerCase();
		return artists.stream().filter((Artist a) -> {
			return a.getName().toLowerCase().contains(lowerQuery);
		}).collect(Collectors.toList());
	}

	public void onArtistSelected(SelectEvent e) {
		Artist selected = (Artist) e.getObject();
		setSelectedArtist(selected);
	}

	/**
	 * Get the value of selectedArtist
	 *
	 * @return the value of selectedArtist
	 */
	public Artist getSelectedArtist() {
		return selectedArtist;
	}

	/**
	 * Set the value of selectedArtist
	 *
	 * @param selectedArtist new value of selectedArtist
	 */
	public void setSelectedArtist(Artist selectedArtist) {
		if (selectedArtist != null) {
			LOG.log(Level.INFO, "artist <{0}> selected in autocomplete", selectedArtist.getName());
		} else {
			LOG.log(Level.INFO, "no artist selected in autocomplete");
		}
		this.selectedArtist = selectedArtist;
	}

	public String getSelectedArtistLink() {
		Artist a = getSelectedArtist();
		if (a == null) {
			LOG.log(Level.WARNING, "no artist set for autocomplete");
			return "artists";
		}
		LOG.log(Level.INFO, "generating link for artist <{0}> ", a.getName());
		return "artists"
			+ "?categoryId=" + a.getCategoryId()
			+ "&artistId=" + a.getId();
	}
	
	public String getSelectedValue() {
		return getSelectedArtist()== null ? "" : getSelectedArtist().getName();
	}

}
