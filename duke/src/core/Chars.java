package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Chars {

	private static HashMap<String, Integer> tileIDs;
	private static HashMap<Integer, ArrayList<Character>> tileIDCharMap;

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
	}

	public static Integer getTileID(String s) {
		return tileIDs.get(s);
	}

	public static Character getRandomCharacter(int tileID) {
		ArrayList<Character> list = tileIDCharMap.get(tileID);
		return list.get(new Random().nextInt(list.size()));
	}

}
