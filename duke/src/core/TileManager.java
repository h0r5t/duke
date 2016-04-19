package core;

import java.util.HashMap;

public abstract class TileManager {

	private static HashMap<Integer, Tile> tileMap;

	public static void load() {
		tileMap = new HashMap<Integer, Tile>();
		tileMap.put(-1, new TileOOB());
		tileMap.put(0, new TileLand());
		tileMap.put(1, new TileWater());
	}

	public static Tile getTileWithID(int id) {
		return tileMap.get(id);
	}

}
