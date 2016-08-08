package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemStorage {

	private HashMap<Integer, ArrayList<Item>> tileItemsMap;
	private HashMap<Item, Coords3D> itemsCoordsMap;
	private ArrayList<Item> unclaimedItems;

	public ItemStorage() {
		tileItemsMap = new HashMap<>();
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
		int id = c.getTile().id();
		if (tileItemsMap.containsKey(id)) {
			tileItemsMap.get(id).add(i);
		} else {
			ArrayList<Item> newList = new ArrayList<>();
			newList.add(i);
			tileItemsMap.put(id, newList);
		}
		itemsCoordsMap.put(i, c);
	}

	public void removeItem(Item i) {
		int tileID = itemsCoordsMap.get(i).getTile().id();
		itemsCoordsMap.remove(i);
		tileItemsMap.get(tileID).remove(i);
	}

	public ArrayList<Item> getItemsAt(Coords3D c) {
		if (tileItemsMap.get(c.getTile().id()) == null)
			return new ArrayList<Item>();
		return tileItemsMap.get(c.getTile().id());
	}

	public Coords3D getItemPos(Item item) {
		return itemsCoordsMap.get(item);
	}

	public ArrayList<Item> getItems() {
		return new ArrayList<Item>(itemsCoordsMap.keySet());
	}

	public void update(Core core) {
		checkIfThereIsSpaceForItemsNow(core);
	}

	private void checkIfThereIsSpaceForItemsNow(Core core) {
		for (Item i : getUnclaimedItems()) {
			Stockpile p = core.getLogisticsManager().getStockPileManager()
					.getStockpileForItem(i);
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
