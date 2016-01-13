package wea.ufo.data;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import wea.ufo.util.ServiceLocator;
import wea.ufo.ws.Category;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
@Named
@RequestScoped
public class CategoryData {
	
	private static final Logger LOG = Logger.getLogger(CategoryData.class.getName());
	private List<Category> categories;
	private Category selectedCategory;
	
	public CategoryData() {
		LOG.log(Level.INFO, "created categoryData");
	}
	
	@PostConstruct
	public void init() {
		LOG.log(Level.INFO, "loading categories");
		categories = ServiceLocator.getInstance().getUFOBusinessDelegate().getCategories();
		if (!categories.isEmpty()) {
			setSelectedCategory(categories.get(0));
		}
	}

	/**
	 * Get the value of categories
	 *
	 * @return the value of categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * Set the value of categories
	 *
	 * @param categories new value of categories
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	/**
	 * Get the value of selectedCategory
	 *
	 * @return the value of selectedCategory
	 */
	public Category getSelectedCategory() {
		return selectedCategory;
	}

	/**
	 * Set the value of selectedCategory
	 *
	 * @param selectedCategory new value of selectedCategory
	 */
	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	public void onCategorySelected(SelectEvent e) {
		Category cat = (Category) e.getObject();
		setSelectedCategory(cat);
		LOG.log(Level.INFO, "category <{0}> selected", getSelectedCategory().getId());
	}
	
}
