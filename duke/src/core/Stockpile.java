package core;

import java.util.ArrayList;

public class Stockpile {

	private Zone2D zone;
	private DefaultHashMap<Coords3D, Boolean> slotUsedMap;
	private MultiMap<Class<? extends Item>, Item> itemsMap;

	public Stockpile(Zone2D zone) {
		this.zone = zone;
		this.slotUsedMap = new DefaultHashMap<>();
		this.itemsMap = new MultiMap<>();
		setGround();
	}

	public Item getItemOfType(Class<? extends Item> i) {
		return itemsMap.removeAny(i);
	}

	public boolean hasItemOfType(Class<? extends Item> i) {
		return itemsMap.hasAtLeastOneOf(i);
	}

	public int getItemAmountOfType(Class<? extends Item> i) {
		return itemsMap.count(i);
	}

	public ArrayList<Item> getItemsOfType(Class<? extends Item> i, int amount) {
		// returns list of items and removes them, returns null if there are
		// less items than amount.
		return itemsMap.removeAmount(i, amount);
	}

	public void addItem(Item item) {
		itemsMap.putOne(item.getClass(), item);
	}

	public void removeItem(Item item) {
		itemsMap.removeOne(item.getClass(), item);
	}

	public Zone2D getZone() {
		return zone;
	}

	public void setZone(Zone2D zone) {
		this.zone = zone;
	}

	public Coords3D getNextFreeSlot() {
		for (Coords3D c : zone.getCoords()) {
			if (slotUsedMap.getDefault(c, false) == false && !c.getTile().blocksPath())
				return c;
		}
		return null;
	}

	public boolean isFull() {
		return getNextFreeSlot() == null;
	}

	public void markAsUsed(Coords3D dropLocation) {
		slotUsedMap.put(dropLocation, true);
	}

	public void setGround() {
		for (Coords3D c : zone.getCoords()) {
			c.getTile().setGround(new GroundStockpile());
		}
	}

}
