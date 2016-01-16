package wea.ufo.data;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.SelectEvent;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Artist;
import wea.ufo.ws.Category;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@ViewScoped
public class ArtistData implements Serializable {

	private static final Logger LOG = Logger.getLogger(ArtistData.class.getName());
	private List<Artist> artists;
	private Artist selectedArtist;
	private Category selectedCategory;
	@Inject
	private CategoryData categoryData;
	@Inject
	transient private ServiceLocator serviceLocator;
	@Inject
	private HttpServletRequest httpServletRequest;

	/**
	 * Set the value of serviceLocator
	 *
	 * @param serviceLocator new value of serviceLocator
	 */
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	/**
	 * Set the value of httpServletRequest
	 *
	 * @param request
	 */
	public void setHttpServletRequest(HttpServletRequest request) {
		httpServletRequest = request;
	}

	public ArtistData() {
		LOG.log(Level.INFO, "created artistData");
	}

	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "initializing artistData");
	}

	private void loadArtistFromParameterOrDefault() {
		if (artists.isEmpty()) {
			// nothing to select
			setSelectedArtist(null);
			return;
		}

		String parameter = httpServletRequest.getParameter("artistId");
		if (parameter != null) {
			final int parameterId = Integer.parseInt(parameter);
			
			// try to locate get-parameter artist
			try {
				Artist fromParameter = artists.stream().filter((Artist a) -> {
					return a.getId() == parameterId;
				}).findFirst().get();
				setSelectedArtist(fromParameter);
				LOG.log(Level.INFO, "selected artist {0} based on get-parameter", fromParameter.getId());
			} catch (NoSuchElementException e) {
				// use default - nothing to do
				setSelectedArtist(null);
				LOG.log(Level.WARNING, "could not select category {0} based on get-parameter", parameterId);
			}
		} else {
			setSelectedArtist(artists.get(0));
		}
	}

	/**
	 * Get the value of artists
	 *
	 * @return the value of artists
	 */
	public List<Artist> getArtists() {
		loadArtists();
		return artists;
	}

	private void loadArtists() {
		Category cat = categoryData.getSelectedCategory();
		if (cat != selectedCategory) {
			selectedCategory = cat;
			LOG.log(Level.INFO, "loading artists for category <{0}> ", cat.getId());
			artists = serviceLocator.getUFOBusinessDelegate().getArtistsForCategory(cat);
			loadArtistFromParameterOrDefault();
		}
	}

	/**
	 * Set the value of artists
	 *
	 * @param artists new value of artists
	 */
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	/**
	 * Get the value of categoryData
	 *
	 * @return the value of categoryData
	 */
	public CategoryData getCategoryData() {
		return categoryData;
	}

	/**
	 * Set the value of categoryData
	 *
	 * @param categoryData new value of categoryData
	 */
	public void setCategoryData(CategoryData categoryData) {
		this.categoryData = categoryData;
	}

	/**
	 * Get the value of selectedArtist
	 *
	 * @return the value of selectedArtist
	 */
	public Artist getSelectedArtist() {
		LOG.log(Level.INFO, "artist <{0}> selected", selectedArtist == null ? "non" : selectedArtist.getId());
		loadArtists();
		return selectedArtist;
	}

	/**
	 * Set the value of selectedArtist
	 *
	 * @param selectedArtist new value of selectedArtist
	 */
	public void setSelectedArtist(Artist selectedArtist) {
		this.selectedArtist = selectedArtist;
	}

	public void onArtistSelected(SelectEvent e) {
		LOG.log(Level.INFO, e.getObject() == null ? "nuul" : "nnul");
		Artist art = (Artist) e.getObject();
		setSelectedArtist(art);
		if (art != null) {
			LOG.log(Level.INFO, "artist <{0}> selected", art.getName());
		}
	}

}
