package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Stockpile {

	private Zone2D zone;
	private ArrayList<Integer> forbiddenItemIDs;
	private HashMap<Coords3D, Boolean> slotUsedMap;
	private HashMap<Integer, Integer> itemIDsAmountMap;

	public Stockpile(Zone2D zone) {
		this.zone = zone;
		this.forbiddenItemIDs = new ArrayList<>();
		this.slotUsedMap = new HashMap<>();
		this.itemIDsAmountMap = new HashMap<>();
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
			ArrayList<Item> items = Core.getWorld().getItemsAt(c);
			if (items.size() > 0) {
				slotUsedMap.put(c, true);
			} else
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
			if (slotUsedMap.get(c) == false && !c.getTile().collides())
				return c;
		}
		return null;
	}

	public ArrayList<Item> getItemsOfType(int itemID, int amount) {
		// TEST TEST TEST wip
		itemIDsAmountMap.clear();

		ArrayList<Item> foundItems = new ArrayList<Item>();
		for (Coords3D coords : zone.getCoords()) {
			ArrayList<Item> items = Core.getWorld().getItemsAt(coords);
			for (Item i : items) {
				if (i.getItemID() == itemID && foundItems.size() < amount)
					foundItems.add(i);
				if (itemIDsAmountMap.containsKey(i.getItemID())) {
					itemIDsAmountMap.put(i.getItemID(), itemIDsAmountMap.get(i.getItemID()) + 1);
				} else
					itemIDsAmountMap.put(i.getItemID(), 1);
			}
		}
		return foundItems;
	}

	public Item getItemOfType(int itemID) {
		// the map gets refreshed on each call of this method
		itemIDsAmountMap.clear();

		Item foundItem = null;
		for (Coords3D coords : zone.getCoords()) {
			ArrayList<Item> items = Core.getWorld().getItemsAt(coords);
			for (Item i : items) {
				if (i.getItemID() == itemID)
					foundItem = i;
				if (itemIDsAmountMap.containsKey(i.getItemID())) {
					itemIDsAmountMap.put(i.getItemID(), itemIDsAmountMap.get(i.getItemID()) + 1);
				} else
					itemIDsAmountMap.put(i.getItemID(), 1);
			}
		}
		return foundItem;
	}

	public boolean hasItemOfType(int itemID) {
		// the map gets refreshed on each call of this method
		itemIDsAmountMap.clear();

		boolean foundItem = false;
		for (Coords3D coords : zone.getCoords()) {
			ArrayList<Item> items = Core.getWorld().getItemsAt(coords);
			for (Item i : items) {
				if (i.getItemID() == itemID)
					foundItem = true;
				if (itemIDsAmountMap.containsKey(i.getItemID())) {
					itemIDsAmountMap.put(i.getItemID(), itemIDsAmountMap.get(i.getItemID()) + 1);
				} else
					itemIDsAmountMap.put(i.getItemID(), 1);
			}
		}
		return foundItem;
	}

	public void markAsUsed(Coords3D dropLocation) {
		slotUsedMap.put(dropLocation, true);
	}

	public boolean allowsItem(Item i) {
		if (forbiddenItemIDs.contains(i.getItemID()))
			return false;
		return true;
	}

	public boolean allowsItem(int itemID) {
		if (forbiddenItemIDs.contains(itemID))
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
