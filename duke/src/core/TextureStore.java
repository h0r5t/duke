package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TextureStore {

	private static HashMap<Integer, ArrayList<TextureCharacter>> tileTextures;
	private static HashMap<Integer, TextureFill> groundTextures;
	private static HashMap<Integer, ArrayList<TextureCharacter>> unitTextures;
	private static HashMap<Integer, ArrayList<TextureCharacter>> itemTextures;
	private static HashMap<Direction, TextureBorder[]> borderTextures;
	private static TextureFill darknessBackgroundTexture;
	private static TextureCharacter[] darknessTextures;

	public static void load() {
		tileTextures = new HashMap<>();
		groundTextures = new HashMap<>();
		unitTextures = new HashMap<>();
		itemTextures = new HashMap<>();
		borderTextures = new HashMap<>();

		darknessBackgroundTexture = new TextureFill(Colors.COLOR_DARKNESS);
		darknessTextures = new TextureCharacter[4];

		loadDarknessTextures();
		loadTileTextures();
		loadGroundTextures();
		loadUnitTextures();
		loadItemTextures();
		loadBorderTextures();
	}

	private static void loadDarknessTextures() {
		darknessTextures[0] = new TextureCharacter(new Character(" ", Color.LIGHT_GRAY, 16));
		darknessTextures[1] = new TextureCharacter(new Character("'", Color.LIGHT_GRAY, 16));
		darknessTextures[2] = new TextureCharacter(new Character(",", Color.LIGHT_GRAY, 16));
		darknessTextures[3] = new TextureCharacter(new Character("´", Color.LIGHT_GRAY, 16));
	}

	public static TextureFill getDarknessBackgroundTexture() {
		return darknessBackgroundTexture;
	}

	public static TextureCharacter getDarknessForeGroundTexture(int id) {
		return darknessTextures[id];
	}

	private static void loadBorderTextures() {
		for (Direction loc : Direction.values()) {
			TextureBorder[] texArray = new TextureBorder[2];
			texArray[0] = new TextureBorder(0.01f, loc);
			texArray[1] = new TextureBorder(4f, loc);
			borderTextures.put(loc, texArray);
		}
	}

	private static void loadTileTextures() {
		for (int id : GameData.getTileIDs().values()) {
			ArrayList<TextureCharacter> list = new ArrayList<>();

			ArrayList<Character> chars = GameData.getAllTileCharacters(id);
			for (Character c : chars) {
				TextureCharacter t = new TextureCharacter(c);
				list.add(t);
			}
			tileTextures.put(id, list);
		}
	}

	private static void loadUnitTextures() {
		for (int id : GameData.getUnitIDs().values()) {
			ArrayList<TextureCharacter> list = new ArrayList<>();

			ArrayList<Character> chars = GameData.getAllUnitCharacters(id);
			for (Character c : chars) {
				TextureCharacter t = new TextureCharacter(c);
				list.add(t);
			}
			unitTextures.put(id, list);
		}
	}

	private static void loadItemTextures() {
		for (int id : GameData.getItemIDs().values()) {
			ArrayList<TextureCharacter> list = new ArrayList<>();

			ArrayList<Character> chars = GameData.getAllItemCharacters(id);
			for (Character c : chars) {
				TextureCharacter t = new TextureCharacter(c);
				list.add(t);
			}
			itemTextures.put(id, list);
		}
	}

	private static void loadGroundTextures() {
		for (int id : GameData.getGroundIDs().values()) {
			TextureFill t = new TextureFill(GameData.getGroundColor(id));
			groundTextures.put(id, t);
		}
	}

	public static TextureCharacter getTileTexture(int tileID, Character c) {
		for (TextureCharacter t : tileTextures.get(tileID)) {
			if (t.getCharacter().getChar() == c.getChar()) {
				return t;
			}
		}

		return null;
	}

	public static TextureFill getGroundTexture(int groundID) {
		return groundTextures.get(groundID);
	}

	public static TextureCharacter getUnitTexture(int unitID, Character c) {
		for (TextureCharacter t : unitTextures.get(unitID)) {
			if (t.getCharacter().getChar() == c.getChar()) {
				return t;
			}
		}

		return null;
	}

	public static TextureCharacter getItemTexture(int itemID, Character c) {
		for (TextureCharacter t : itemTextures.get(itemID)) {
			if (t.getCharacter().getChar() == c.getChar()) {
				return t;
			}
		}

		return null;
	}

	public static TextureBorder getBorderTexture(Direction location, int strokeNum) {
		return borderTextures.get(location)[strokeNum];
	}

	public static int getDarknessTextureID() {
		int r = new Random().nextInt(100);
		if (r == 0) {
			return 1;
		}
		if (r == 1) {
			return 2;
		}
		if (r == 2) {
			return 3;
		}
		return 0;
	}

}
