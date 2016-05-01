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

		tileIDCharMap = new HashMap<Integer, ArrayList<Character>>();
		ArrayList<Character> list = new ArrayList<Character>();

		list.add(new Character(".", Colors.COLOR_LAND));
		list.add(new Character(",", Colors.COLOR_LAND));
		list.add(new Character("'", Colors.COLOR_LAND));
		list.add(new Character("`", Colors.COLOR_LAND));
		list.add(new Character("∙", Colors.COLOR_LAND));
		tileIDCharMap.put(0, list);

		list = new ArrayList<Character>();
		list.add(new Character("♣", Colors.COLOR_WOODS));
		tileIDCharMap.put(1, list);

		list = new ArrayList<Character>();
		list.add(new Character("≈", Colors.COLOR_WATER));
		tileIDCharMap.put(2, list);

		// UNITS

		unitIDs = new HashMap<String, Integer>();
		unitIDs.put("unit_worker", 0);

		unitIDCharMap = new HashMap<Integer, ArrayList<Character>>();
		list = new ArrayList<Character>();

		list.add(new Character("☺", Color.ORANGE));
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
