package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TextureStore {

	private static HashMap<Integer, TextureImage> unitTextures;
	private static HashMap<Integer, ArrayList<Texture>> tileTextures;
	private static HashMap<Integer, TextureFill> groundTextures;
	private static HashMap<Integer, TextureImage> itemTextures;
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
		for (String nameID : GameData.getTileIDs().keySet()) {
			int numID = GameData.getTileIDs().get(nameID);

			ArrayList<Texture> list = new ArrayList<>();

			BufferedImage tileTexture = FileSystem.loadImage(FileSystem.TEXTURES_TILES_DIR, nameID);
			if (tileTexture != null) {
				TextureImage tex = new TextureImage(tileTexture);
				list.add(tex);
				tileTextures.put(numID, list);
			}

			else {
				ArrayList<Character> chars = GameData.getAllTileCharacters(numID);
				for (Character c : chars) {
					TextureCharacter t = new TextureCharacter(c);
					list.add(t);
				}
				tileTextures.put(numID, list);
			}

		}
	}

	private static void loadUnitTextures() {
		for (String unitID : GameData.getUnitIDs().keySet()) {
			int numID = GameData.getUnitIDs().get(unitID);
			BufferedImage img = FileSystem.loadImage(FileSystem.TEXTURES_UNITS_DIR, unitID);
			unitTextures.put(numID, new TextureImage(img));
		}
	}

	private static void loadItemTextures() {
		for (String itemID : GameData.getItemIDs().keySet()) {
			int numID = GameData.getItemIDs().get(itemID);
			BufferedImage img = FileSystem.loadImage(FileSystem.TEXTURES_ITEMS_DIR, itemID);
			itemTextures.put(numID, new TextureImage(img));
		}
	}

	private static void loadGroundTextures() {
		for (int id : GameData.getGroundIDs().values()) {
			TextureFill t = new TextureFill(GameData.getGroundColor(id));
			groundTextures.put(id, t);
		}
	}

	public static Texture getTileTexture(int tileID, Character c) {
		for (Texture t : tileTextures.get(tileID)) {
			if (t instanceof TextureImage)
				return t;
			TextureCharacter tc = (TextureCharacter) t;
			if (tc.getCharacter().getChar() == c.getChar()) {
				return tc;
			}
		}

		return null;
	}

	public static TextureFill getGroundTexture(int groundID) {
		return groundTextures.get(groundID);
	}

	public static TextureImage getUnitTexture(int unitID) {
		return unitTextures.get(unitID);
	}

	public static TextureImage getItemTexture(int itemID) {
		return itemTextures.get(itemID);
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
