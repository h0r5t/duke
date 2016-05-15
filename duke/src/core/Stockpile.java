package core;

import java.util.ArrayList;

public class Stockpile {

	private Zone2D zone;
	private ArrayList<Integer> forbiddenItemIDs;

	public Stockpile(Zone2D zone) {
		this.zone = zone;
		this.forbiddenItemIDs = new ArrayList<>();
	}

	public Stockpile(Zone2D zone, ArrayList<Integer> forbiddenItemIDs) {
		this.zone = zone;
		this.forbiddenItemIDs = forbiddenItemIDs;
	}

	public Zone2D getZone() {
		return zone;
	}

	public void setZone(Zone2D zone) {
		this.zone = zone;
	}

	public boolean allowsItem(Item i) {
		if (forbiddenItemIDs.contains(i.getItemID()))
			return false;
		return true;
	}

	public void setMarkers(boolean b) {
		if (b) {
			for (Coords3D c : zone.getCoords()) {
				c.getTile().select(zone);
			}
		} else {
			for (Coords3D c : zone.getCoords()) {
				c.getTile().deselect(zone);
			}
		}

	}

}
