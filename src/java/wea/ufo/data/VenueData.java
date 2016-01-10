package wea.ufo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import wea.ufo.model.*;

/**
 * @author Christoph Wurst
 */
@ManagedBean(name = "venueData")
public class VenueData {

    private static final Logger logger = Logger.getAnonymousLogger();

    public VenueData() {
        logger.log(Level.INFO, "VenueData created");
    }

    public List<Venue> getVenues() {
        List<Venue> result = new ArrayList<>();
        Venue v1 = new Venue();
        v1.setName("V1");
        Venue v2 = new Venue();
        v2.setName("V2");
        result.add(v1);
        result.add(v2);
        return result;
    }

    public List<Area> getAreas() {
        List<Area> result = new ArrayList<>();
        Area a1 = new Area();
        a1.setId(1);
        a1.setName("Hauptplatz");
        result.add(a1);
        Area a2 = new Area();
        a2.setId(2);
        a2.setName("Rathausplatz");
        result.add(a2);
        return result;
    }

}
