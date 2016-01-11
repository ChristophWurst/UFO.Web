package wea.ufo.data;

import java.util.List;
import wea.ufo.model.Area;
import wea.ufo.model.Venue;

/**
 * @author Christoph Wurst <christoph@winzerhof-wurst.at>
 */
public interface UFOBusinessDelegate {

    public List<Area> getAreas();

    public List<Venue> getVenuesForArea(Area a);

    public boolean Login(String email, String password);
}
