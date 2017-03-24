package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager {

	private Core core;
	private HashMap<Coords3D, ArrayList<Item>> coordsItemsMap;
	private HashMap<Item, Coords3D> itemsCoordsMap;

	public ItemManager(Core core) {
		this.core = core;
		coordsItemsMap = new HashMap<>();
		itemsCoordsMap = new HashMap<>();
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

	public void claimAllItemsInZone(Zone2D zone) {
		for (Coords3D c : zone.getCoords()) {
			for (Item i : Core.getWorld().getItemsAt(c)) {
				if (!i.isClaimed())
					claimItem(i);
			}
		}
	}

	public void claimItem(Item i) {
		i.setClaimed(true);
		TaskHaul haulTask = new TaskHaul(i);
		core.getTaskDistributor().addTask(haulTask);
	}

	public void update(Core core) {
	}

}
