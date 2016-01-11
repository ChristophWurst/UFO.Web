package wea.ufo.model;

/**
 * @author Christoph Wurst
 */
public class Venue {

	private int id;

	/**
	 * Get the value of id
	 *
	 * @return the value of id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the value of id
	 *
	 * @param id new value of id
	 */
	public void setId(int id) {
		this.id = id;
	}

	private String name;

	public void setName(String val) {
		name = val;
	}

	public String getName() {
		return name;
	}

	private int areaId;

	/**
	 * Get the value of areaId
	 *
	 * @return the value of areaId
	 */
	public int getAreaId() {
		return areaId;
	}

	/**
	 * Set the value of areaId
	 *
	 * @param areaId new value of areaId
	 */
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

}
