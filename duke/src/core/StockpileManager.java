package core;

import java.util.ArrayList;
import java.util.HashMap;

public class StockpileManager {

	private HashMap<Coords3D, Stockpile> tileIDStockpileMap;

	public StockpileManager() {
		tileIDStockpileMap = new HashMap<>();
	}

	public void addStockpile(Stockpile s) {
		for (Coords3D c : s.getZone().getCoords()) {
			tileIDStockpileMap.put(c, s);
		}
	}

	public void removeStockpile(Stockpile s) {
		for (Coords3D c : s.getZone().getCoords()) {
			tileIDStockpileMap.remove(c);
		}
	}

	public Stockpile getStockpileForPos(Coords3D c) {
		return tileIDStockpileMap.get(c);
	}

	private ArrayList<Stockpile> getStockpiles() {
		return new ArrayList<Stockpile>(tileIDStockpileMap.values());
	}

	public Stockpile getStockpileForItem(Class<? extends Item> i) {
		for (Stockpile s : getStockpiles()) {
			if (s.getNextFreeSlot() != null)
				return s;
		}
		return null;
	}

	public Stockpile getStockpileWithItem(Class<? extends Item> i) {
		for (Stockpile s : getStockpiles()) {
			if (s.hasItemOfType(i))
				return s;
		}

		return null;
	}
}
