package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Chars {

	private static HashMap<String, Integer> tileIDs;
	private static HashMap<String, Integer> unitIDs;

	private static HashMap<Integer, ArrayList<Character>> tileIDCharMap;
	private static HashMap<Integer, ArrayList<Character>> unitIDCharMap;

	public static void load() {
		tileIDs = new HashMap<String, Integer>();
		tileIDs.put("tile_land", 0);
		tileIDs.put("tile_woods", 1);
		tileIDs.put("tile_water", 2);
		tileIDs.put("tile_ladderup", 3);
		tileIDs.put("tile_ladderdown", 4);
		tileIDs.put("tile_rock", 5);
		tileIDs.put("tile_bush", 6);
		tileIDs.put("tile_stone", 7);
		tileIDs.put("tile_mushroom", 8);

		tileIDCharMap = new HashMap<Integer, ArrayList<Character>>();
		ArrayList<Character> list = new ArrayList<Character>();

		list.add(new Character(".", Colors.COLOR_LAND, 18));
		list.add(new Character(",", Colors.COLOR_LAND, 18));
		list.add(new Character("'", Colors.COLOR_LAND, 18));
		list.add(new Character("`", Colors.COLOR_LAND, 18));
		list.add(new Character("∙", Colors.COLOR_LAND, 18));
		tileIDCharMap.put(0, list);

		list = new ArrayList<Character>();
		list.add(new Character("♣", Colors.COLOR_WOODS, 30));
		list.add(new Character("♠", Colors.COLOR_WOODS, 30));
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
		list.add(new Character("■", Color.DARK_GRAY, 26));
		tileIDCharMap.put(5, list);

		list = new ArrayList<Character>();
		list.add(new Character("¥", Colors.COLOR_BUSH, 14));
		list.add(new Character("φ", Colors.COLOR_BUSH, 14));
		tileIDCharMap.put(6, list);

		list = new ArrayList<Character>();
		list.add(new Character("•", Colors.COLOR_STONE, 20));
		tileIDCharMap.put(7, list);

		list = new ArrayList<Character>();
		list.add(new Character("♣", Colors.COLOR_MUSHROOM, 14));
		tileIDCharMap.put(8, list);

		// UNITS

		unitIDs = new HashMap<String, Integer>();
		unitIDs.put("unit_worker", 0);

		unitIDCharMap = new HashMap<Integer, ArrayList<Character>>();
		list = new ArrayList<Character>();

		list.add(new Character("☺", Color.ORANGE, 26));
		unitIDCharMap.put(0, list);
	}

	public static Integer getTileID(String s) {
		return tileIDs.get(s);
	}

	public static Integer getUnitID(String s) {
		return unitIDs.get(s);
	}

	public static Character getRandomTileCharacter(int tileID) {
		ArrayList<Character> list = tileIDCharMap.get(tileID);
		return list.get(new Random().nextInt(list.size()));
	}

	public static Character getRandomUnitCharacter(int unitID) {
		ArrayList<Character> list = unitIDCharMap.get(unitID);
		return list.get(new Random().nextInt(list.size()));
	}

}
