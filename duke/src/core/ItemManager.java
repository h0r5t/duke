package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager {

	private HashMap<Coords3D, ArrayList<Item>> coordsItemsMap;
	private HashMap<Item, Coords3D> itemsCoordsMap;
	private ArrayList<Item> unclaimedItems;

	public ItemManager() {
		coordsItemsMap = new HashMap<>();
		itemsCoordsMap = new HashMap<>();
		unclaimedItems = new ArrayList<Item>();
	}

	public void addUnclaimedItem(Item i) {
		unclaimedItems.add(i);
	}

	private ArrayList<Item> getUnclaimedItems() {
		return (ArrayList<Item>) unclaimedItems.clone();
	}

	private void removeUnclaimedItem(Item i) {
		unclaimedItems.remove(i);
	}

	public void addItem(Item i, Coords3D c) {
		if (coordsItemsMap.containsKey(c)) {
			coordsItemsMap.get(c).add(i);
		} else {
			ArrayList<Item> newList = new ArrayList<>();
			newList.add(i);
			coordsItemsMap.put(c, newList);
		}
		itemsCoordsMap.put(i, c);
	}

	public void removeItem(Item i) {
		Coords3D coords = itemsCoordsMap.get(i);
		itemsCoordsMap.remove(i);
		coordsItemsMap.get(coords).remove(i);
	}

	public ArrayList<Item> getItemsAt(Coords3D c) {
		if (coordsItemsMap.get(c) == null)
			return new ArrayList<Item>();
		return coordsItemsMap.get(c);
	}

	public Coords3D getItemPos(Item item) {
		return itemsCoordsMap.get(item);
	}

	public ArrayList<Item> getItems() {
		return new ArrayList<Item>(itemsCoordsMap.keySet());
	}

	public void update(Core core) {
		checkForItemsToBeHauled(core);
	}

	private void checkForItemsToBeHauled(Core core) {
		for (Item i : getUnclaimedItems()) {
			Stockpile p = core.getLogisticsManager().getStockPileManager().getStockpileForItem(i);
			if (p != null) {
				Coords3D slot = p.getNextFreeSlot();
				if (slot != null) {
					TaskHaul haulTask = new TaskHaul(i, p);
					core.getTaskDistributor().addTask(haulTask);
					removeUnclaimedItem(i);
				}
			}
		}
	}
}
