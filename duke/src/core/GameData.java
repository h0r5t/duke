package core;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameData {

	private static HashMap<String, Integer> tileIDs;
	private static HashMap<String, Integer> groundIDs;
	private static HashMap<String, Integer> unitIDs;
	private static HashMap<String, Integer> itemIDs;

	private static HashMap<Integer, ArrayList<Character>> tileIDCharMap;
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

		groundIDColorMap = new HashMap<>();
		groundIDColorMap.put(0, Colors.COLOR_AIR);
		groundIDColorMap.put(1, Colors.COLOR_GROUND_ROCK);
		groundIDColorMap.put(2, Colors.COLOR_GROUND_GRASS);
		groundIDColorMap.put(3, Colors.COLOR_GROUND_WATER);
		groundIDColorMap.put(4, Colors.COLOR_GROUND_STOCKPILE);
		groundIDColorMap.put(5, Color.WHITE);
	}

	public static void loadTiles() {
		tileIDs = new HashMap<String, Integer>();
		tileIDs.put("tile_oob", -1);
		tileIDs.put("tile_land", 0);
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

		tileIDCharMap = new HashMap<Integer, ArrayList<Character>>();
		ArrayList<Character> list = new ArrayList<Character>();

		list = new ArrayList<Character>();
		list.add(new Character(" ", Color.BLACK, 20));
		tileIDCharMap.put(-1, list);

		list.add(new Character(".", Colors.COLOR_LAND, 18));
		list.add(new Character(",", Colors.COLOR_LAND, 18));
		list.add(new Character("'", Colors.COLOR_LAND, 18));
		list.add(new Character("`", Colors.COLOR_LAND, 18));
		list.add(new Character("∙", Colors.COLOR_LAND, 18));
		tileIDCharMap.put(0, list);

		list = new ArrayList<Character>();
		list.add(new Character("♣", Colors.COLOR_WOOD, 30));
		list.add(new Character("♠", Colors.COLOR_WOOD, 30));
		tileIDCharMap.put(1, list);

		list = new ArrayList<Character>();
		list.add(new Character("≈", Colors.COLOR_WATER, 36));
		tileIDCharMap.put(2, list);

		list = new ArrayList<Character>();
		list.add(new Character("▲", Color.GRAY, 26));
		tileIDCharMap.put(3, list);

		list = new ArrayList<Character>();
		list.add(new Character("▼", Color.GRAY, 26));
		tileIDCharMap.put(4, list);

		list = new ArrayList<Character>();
		// "■""■""■""■""■""■"
		list.add(new Character("FILL", Color.DARK_GRAY, 35));
		tileIDCharMap.put(5, list);

		list = new ArrayList<Character>();
		list.add(new Character("¥", Colors.COLOR_BUSH, 14));
		list.add(new Character("φ", Colors.COLOR_BUSH, 14));
		list.add(new Character("♣", Colors.COLOR_BUSH, 14));
		list.add(new Character("♠", Colors.COLOR_BUSH, 14));
		tileIDCharMap.put(6, list);

		list = new ArrayList<Character>();
		list.add(new Character("•", Colors.COLOR_STONE, 20));
		tileIDCharMap.put(7, list);

		list = new ArrayList<Character>();
		list.add(new Character("♣", Colors.COLOR_MUSHROOM, 14));
		tileIDCharMap.put(8, list);

		list = new ArrayList<Character>();
		list.add(new Character(" ", Color.BLUE, 24));
		tileIDCharMap.put(9, list);

		list = new ArrayList<Character>();
		list.add(new Character(" ", Color.WHITE, 24));
		tileIDCharMap.put(10, list);

		list = new ArrayList<Character>();
		list.add(new Character("▲", Color.GRAY, 20));
		tileIDCharMap.put(11, list);

		list = new ArrayList<Character>();
		list.add(new Character("O", Colors.COLOR_TRUNK, 24, Colors.COLOR_WOOD));
		tileIDCharMap.put(12, list);

		list = new ArrayList<Character>();
		list.add(new Character("¼", Colors.COLOR_LEAVES, 20, Colors.COLOR_GROUND_LEAVES));
		tileIDCharMap.put(13, list);

		// buidlings

		list = new ArrayList<Character>();
		list.add(new Character("#", Color.WHITE, 24));
		tileIDCharMap.put(100, list);
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

	public static Character getRandomTileCharacter(int tileID) {
		ArrayList<Character> list = tileIDCharMap.get(tileID);
		if (list == null)
			return new Character("", null, 0);
		return list.get(new Random().nextInt(list.size()));
	}

	public static ArrayList<Character> getAllTileCharacters(int tileID) {
		ArrayList<Character> list = tileIDCharMap.get(tileID);
		return list;
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
