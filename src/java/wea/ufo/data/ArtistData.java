package wea.ufo.data;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Artist;
import wea.ufo.ws.Category;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@RequestScoped
public class ArtistData {

	private static final Logger LOG = Logger.getLogger(ArtistData.class.getName());
	private List<Artist> artists;
	private Artist selectedArtist;
	private Category selectedCategory;
	@Inject
	private CategoryData categoryData;

	public ArtistData() {
		LOG.log(Level.INFO, "created artistData");
	}

	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "initializing artistData");
	}

	/**
	 * Get the value of artists
	 *
	 * @return the value of artists
	 */
	public List<Artist> getArtists() {
		Category cat = categoryData.getSelectedCategory();
		if (cat != selectedCategory) {
			selectedCategory = cat;
			LOG.log(Level.INFO, "loading artists for category <{0}> ", cat.getDescription());
			artists = ServiceLocator.getInstance().getUFOBusinessDelegate().getArtistsForCategory(cat);
			if (!artists.isEmpty()) {
				setSelectedArtist(artists.get(0));
			}
		}
		return artists;
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
