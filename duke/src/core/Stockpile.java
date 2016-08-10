package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Stockpile {

	private Zone2D zone;
	private ArrayList<Integer> forbiddenItemIDs;
	private HashMap<Coords3D, Boolean> slotUsedMap;

	public Stockpile(Zone2D zone) {
		this.zone = zone;
		this.forbiddenItemIDs = new ArrayList<>();
		this.slotUsedMap = new HashMap<>();
		scanForUsedSlots();
		setMarkers(true);
	}

	public Stockpile(Zone2D zone, ArrayList<Integer> forbiddenItemIDs) {
		this.zone = zone;
		this.forbiddenItemIDs = forbiddenItemIDs;
		this.slotUsedMap = new HashMap<>();
		scanForUsedSlots();
	}

	private void scanForUsedSlots() {
		for (Coords3D c : zone.getCoords()) {
			if (Core.getWorld().getItemsAt(c).size() > 0)
				slotUsedMap.put(c, true);
			else
				slotUsedMap.put(c, false);
		}
	}

	public Zone2D getZone() {
		return zone;
	}

	public void setZone(Zone2D zone) {
		this.zone = zone;
	}

	public Coords3D getNextFreeSlot() {
		for (Coords3D c : zone.getCoords()) {
			if (slotUsedMap.get(c) == false)
				return c;
		}
		return null;
	}

	public void markAsUsed(Coords3D dropLocation) {
		slotUsedMap.put(dropLocation, true);
	}

	public boolean allowsItem(Item i) {
		if (forbiddenItemIDs.contains(i.getItemID()))
			return false;
		return true;
	}

	public void setMarkers(boolean b) {
		if (b) {
			for (Coords3D c : zone.getCoords()) {
				c.getTile().setGround(new GroundStockpile());
			}
		} else {
			for (Coords3D c : zone.getCoords()) {
				c.getTile().resetGround();
			}
		}

	}

}
