package core;

import java.util.ArrayList;
import java.util.HashMap;

public class StockpileManager {

	private HashMap<Integer, Stockpile> tileIDStockpileMap;

	public StockpileManager() {
		tileIDStockpileMap = new HashMap<>();
	}

	public void setStockpileMarkers(boolean b) {
		for (Stockpile s : getStockpiles()) {
			s.setMarkers(b);
		}
	}

	public void addStockpile(Stockpile s) {
		for (Coords3D c : s.getZone().getCoords()) {
			tileIDStockpileMap.put(c.getTile().id(), s);
		}
	}

	public void removeStockpile(Stockpile s) {
		s.setMarkers(false);
		for (Coords3D c : s.getZone().getCoords()) {
			tileIDStockpileMap.remove(c.getTile().id());
		}
	}

	public Stockpile getStockpileForPos(Coords3D c) {
		return tileIDStockpileMap.get(c.getTile().id());
	}

	private ArrayList<Stockpile> getStockpiles() {
		return new ArrayList<Stockpile>(tileIDStockpileMap.values());
	}

	public Stockpile getStockpileForItem(Item item) {
		for (Stockpile s : getStockpiles()) {
			if (s.allowsItem(item))
				return s;
		}
		return null;
	}
}
