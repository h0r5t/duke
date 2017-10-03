package core;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class TextureStore {

	private static HashMap<Integer, TextureImage> unitTextures;
	private static HashMap<Integer, Texture> tileTextures;
	private static HashMap<Integer, Texture> groundTextures;
	private static HashMap<Integer, TextureImage> itemTextures;
	private static HashMap<Direction, TextureBorder[]> borderTextures;
	private static TextureFill darknessBackgroundTexture;
	private static Texture[] darknessTextures;
	private static HashMap<String, TextureImage> shadowMap;

	public static void load() {
		tileTextures = new HashMap<>();
		groundTextures = new HashMap<>();
		unitTextures = new HashMap<>();
		itemTextures = new HashMap<>();
		borderTextures = new HashMap<>();
		shadowMap = new HashMap<>();

		darknessBackgroundTexture = new TextureFill(Colors.COLOR_DARKNESS);
		darknessTextures = new Texture[1];

		loadDarknessTextures();
		loadTileTextures();
		loadGroundTextures();
		loadUnitTextures();
		loadItemTextures();
		loadBorderTextures();
		loadShadowTextures();
	}

	private static void loadShadowTextures() {
		shadowMap.put("shadow_bottom",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_bottom")));
		shadowMap.put("shadow_top",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_top")));
		shadowMap.put("shadow_left",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_left")));
		shadowMap.put("shadow_right",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_right")));
		shadowMap.put("shadow_bottom_left",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_bottom_left")));
		shadowMap.put("shadow_bottom_right",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_bottom_right")));
		shadowMap.put("shadow_top_left",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_top_left")));
		shadowMap.put("shadow_top_right",
				new TextureImage(FileSystem.loadImage(FileSystem.TEXTURES_SHADOWS_DIR, "shadow_top_right")));
	}

	public static TextureImage getShadowTexture(String name) {
		return shadowMap.get(name);
	}

	private static void loadDarknessTextures() {
		darknessTextures[0] = new TextureFill(Colors.COLOR_DARKNESS);
	}

	public static TextureFill getDarknessBackgroundTexture() {
		return darknessBackgroundTexture;
	}

	public static Texture getDarknessForeGroundTexture() {
		return darknessTextures[0];
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

			BufferedImage tileTexture = FileSystem.loadImage(FileSystem.TEXTURES_TILES_DIR, nameID);
			if (tileTexture != null) {
				TextureImage tex = new TextureImage(tileTexture);
				tileTextures.put(numID, tex);
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
		for (String nameID : GameData.getGroundIDs().keySet()) {
			int numID = GameData.getGroundIDs().get(nameID);

			BufferedImage groundTexture = FileSystem.loadImage(FileSystem.TEXTURES_GROUNDS_DIR, nameID);
			if (groundTexture != null) {
				TextureImage tex = new TextureImage(groundTexture);
				groundTextures.put(numID, tex);
			}

			else {
				TextureFill t = new TextureFill(GameData.getGroundColor(numID));
				groundTextures.put(numID, t);
			}

		}
	}

	public static Texture getTileTexture(int tileID) {
		Texture t = tileTextures.get(tileID);
		if (t instanceof TextureImage)
			return t;

		return null;
	}

	public static Texture getGroundTexture(int groundID) {
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
