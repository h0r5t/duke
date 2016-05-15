package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemStorage {

	private HashMap<Integer, ArrayList<Item>> tileItemsMap;
	private HashMap<Item, Coords3D> itemsCoordsMap;

	public ItemStorage() {
		tileItemsMap = new HashMap<>();
		itemsCoordsMap = new HashMap<>();
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
}
