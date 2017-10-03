package core;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class GameData {

	private static HashMap<String, Integer> tileIDs;
	private static HashMap<String, Integer> groundIDs;
	private static HashMap<String, Integer> unitIDs;
	private static HashMap<String, Integer> itemIDs;

	private static HashMap<Integer, Color> groundIDColorMap;

	public static void load() {
		loadTiles();
		loadGrounds();
		loadUnits();
		loadItems();
	}

	private static void loadGrounds() {
		groundIDs = new HashMap<String, Integer>();
		groundIDs.put("ground_air", 0);
		groundIDs.put("ground_rock", 1);
		groundIDs.put("ground_grass", 2);
		groundIDs.put("ground_water", 3);
		groundIDs.put("ground_stockpile", 4);
		groundIDs.put("ground_snow", 5);
		groundIDs.put("ground_wood", 6);

		groundIDColorMap = new HashMap<>();
		groundIDColorMap.put(0, Colors.COLOR_AIR);
		groundIDColorMap.put(1, Colors.COLOR_GROUND_ROCK);
		groundIDColorMap.put(2, Colors.COLOR_GROUND_GRASS);
		groundIDColorMap.put(3, Colors.COLOR_GROUND_WATER);
		groundIDColorMap.put(4, Colors.COLOR_GROUND_STOCKPILE);
		groundIDColorMap.put(5, Color.WHITE);
		groundIDColorMap.put(6, Colors.COLOR_ITEM_WOOD);
	}

	public static void loadTiles() {
		tileIDs = new HashMap<String, Integer>();
		tileIDs.put("tile_oob", -1);
		tileIDs.put("tile_grass", 0);
		tileIDs.put("tile_tree", 1);
		tileIDs.put("tile_water", 2);
		tileIDs.put("tile_ladder_up", 3);
		tileIDs.put("tile_ladder_down", 4);
		tileIDs.put("tile_rock", 5);
		tileIDs.put("tile_bush", 6);
		tileIDs.put("tile_stone", 7);
		tileIDs.put("tile_mushroom", 8);
		tileIDs.put("tile_air", 9);
		tileIDs.put("tile_ground", 10);
		tileIDs.put("tile_ramp", 11);
		tileIDs.put("tile_trunk", 12);
		tileIDs.put("tile_leaves", 13);
		tileIDs.put("tile_trunk_on_ground", 14);

		tileIDs.put("building_crafting_table", 100);
	}

	private static void loadUnits() {
		unitIDs = new HashMap<String, Integer>();
		unitIDs.put("unit_worker", 0);
		unitIDs.put("unit_goblin", 1);
	}

	private static void loadItems() {
		itemIDs = new HashMap<String, Integer>();
		itemIDs.put("item_stone", 0);
		itemIDs.put("item_wood", 1);
		itemIDs.put("item_bow", 2);
		itemIDs.put("item_barrel", 3);
	}

	public static Integer getTileID(String s) {
		return tileIDs.get(s);
	}

	public static Integer getGroundID(String s) {
		return groundIDs.get(s);
	}

	public static Integer getUnitID(String s) {
		return unitIDs.get(s);
	}

	public static Integer getItemID(String s) {
		return itemIDs.get(s);
	}

	public static HashMap<String, Integer> getTileIDs() {
		return tileIDs;
	}

	public static HashMap<String, Integer> getGroundIDs() {
		return groundIDs;
	}

	public static HashMap<String, Integer> getUnitIDs() {
		return unitIDs;
	}

	public static HashMap<String, Integer> getItemIDs() {
		return itemIDs;
	}

	public static Color getGroundColor(int groundID) {
		return groundIDColorMap.get(groundID);
	}

	public static Tile getTileInstanceFromId(String className, int x, int y, int z) {
		try {
			Class<?> c = Class.forName("core." + className);

			Constructor<?> ctor = c.getConstructors()[0];
			Object object = ctor.newInstance(new Object[] { x, y, z });

			return (Tile) object;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
