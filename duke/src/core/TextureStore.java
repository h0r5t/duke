package core;

import java.util.ArrayList;
import java.util.HashMap;

public class TextureStore {

	private static HashMap<Integer, ArrayList<TextureCharacter>> tileTextures;
	private static HashMap<Integer, TextureFill> groundTextures;
	private static HashMap<Integer, ArrayList<TextureCharacter>> unitTextures;
	private static HashMap<Integer, ArrayList<TextureCharacter>> itemTextures;
	private static HashMap<BorderLocation, TextureBorder[]> borderTextures;

	public static void load() {
		tileTextures = new HashMap<>();
		groundTextures = new HashMap<>();
		unitTextures = new HashMap<>();
		itemTextures = new HashMap<>();
		borderTextures = new HashMap<>();

		loadTileTextures();
		loadGroundTextures();
		loadUnitTextures();
		loadItemTextures();
		loadBorderTextures();
	}

	private static void loadBorderTextures() {
		for (BorderLocation loc : BorderLocation.values()) {
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

	public static TextureBorder getBorderTexture(BorderLocation location, int strokeNum) {
		return borderTextures.get(location)[strokeNum];
	}
}
