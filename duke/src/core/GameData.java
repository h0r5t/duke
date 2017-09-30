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
	private static HashMap<Integer, ArrayList<Character>> unitIDCharMap;
	private static HashMap<Integer, ArrayList<Character>> itemIDCharMap;

	public static void load() {
		loadTiles();
		loadGrounds();
		loadUnits();
		loadItems();
	}

	private static void loadGrounds() {
		groundIDs = new HashMap<String, Integer>();
		groundIDs.put("ground_black", -1);
		groundIDs.put("ground_air", 0);
		groundIDs.put("ground_rock", 1);
		groundIDs.put("ground_grass", 2);
		groundIDs.put("ground_water", 3);
		groundIDs.put("ground_stockpile", 4);
		groundIDs.put("ground_snow", 5);

		groundIDColorMap = new HashMap<>();
		groundIDColorMap.put(-1, Color.BLACK);
		groundIDColorMap.put(0, Colors.COLOR_AIR);
		groundIDColorMap.put(1, Colors.COLOR_GROUND_ROCK);
		groundIDColorMap.put(2, Colors.COLOR_GROUND_GRASS);
		groundIDColorMap.put(3, Colors.COLOR_GROUND_WATER);
		groundIDColorMap.put(4, Colors.COLOR_GROUND_STOCKPILE);
		groundIDColorMap.put(5, Color.WHITE);
	}

	public static void loadTiles() {
		tileIDs = new HashMap<String, Integer>();
		tileIDs.put("TileOOB", -1);
		tileIDs.put("TileLand", 0);
		tileIDs.put("TileTree", 1);
		tileIDs.put("TileWater", 2);
		tileIDs.put("TileLadderUp", 3);
		tileIDs.put("TileLadderDown", 4);
		tileIDs.put("TileRock", 5);
		tileIDs.put("TileBush", 6);
		tileIDs.put("TileStone", 7);
		tileIDs.put("TileMushroom", 8);
		tileIDs.put("TileAir", 9);
		tileIDs.put("TileGround", 10);
		tileIDs.put("TileRamp", 11);
		tileIDs.put("TileTrunk", 12);
		tileIDs.put("TileLeaves", 13);

		tileIDs.put("BuildingCraftingTable", 100);

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

		unitIDCharMap = new HashMap<Integer, ArrayList<Character>>();

		ArrayList<Character> list = new ArrayList<Character>();
		list.add(new Character("☺", Colors.COLOR_DWARF, 20));
		unitIDCharMap.put(0, list);

		list = new ArrayList<Character>();
		list.add(new Character("g", Color.GREEN, 16));
		unitIDCharMap.put(1, list);
	}

	private static void loadItems() {
		itemIDs = new HashMap<String, Integer>();
		itemIDs.put("item_stone", 0);
		itemIDs.put("item_wood", 1);
		itemIDs.put("item_bow", 2);
		itemIDs.put("item_barrel", 3);

		itemIDCharMap = new HashMap<Integer, ArrayList<Character>>();

		ArrayList<Character> list = new ArrayList<Character>();
		list.add(new Character("•", Colors.COLOR_STONE, 26));
		itemIDCharMap.put(0, list);

		list = new ArrayList<Character>();
		list.add(new Character("/", Colors.COLOR_ITEM_WOOD, 26));
		itemIDCharMap.put(1, list);

		list = new ArrayList<Character>();
		list.add(new Character(")", Colors.COLOR_ITEM_WOOD, 26));
		itemIDCharMap.put(2, list);

		list = new ArrayList<Character>();
		list.add(new Character("Θ", Colors.COLOR_ITEM_WOOD, 20));
		itemIDCharMap.put(3, list);
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
		return list.get(new Random().nextInt(list.size()));
	}

	public static ArrayList<Character> getAllTileCharacters(int tileID) {
		ArrayList<Character> list = tileIDCharMap.get(tileID);
		return list;
	}

	public static Character getRandomUnitCharacter(int unitID) {
		ArrayList<Character> list = unitIDCharMap.get(unitID);
		return list.get(new Random().nextInt(list.size()));
	}

	public static ArrayList<Character> getAllUnitCharacters(int unitID) {
		ArrayList<Character> list = unitIDCharMap.get(unitID);
		return list;
	}

	public static Character getRandomItemCharacter(int itemID) {
		ArrayList<Character> list = itemIDCharMap.get(itemID);
		return list.get(new Random().nextInt(list.size()));
	}

	public static ArrayList<Character> getAllItemCharacters(int itemID) {
		ArrayList<Character> list = itemIDCharMap.get(itemID);
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
