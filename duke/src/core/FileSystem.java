package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileSystem {

	public static final String ROOT_DIR = "./";

	public static final String RES_DIR = ROOT_DIR + "res/";
	public static final String TEXTURES_DIR = RES_DIR + "textures/";
	public static final String TEXTURES_TILES_DIR = TEXTURES_DIR + "tiles/";
	public static final String TEXTURES_UNITS_DIR = TEXTURES_DIR + "units/";
	public static final String TEXTURES_ITEMS_DIR = TEXTURES_DIR + "items/";
	public static final String TEXTURES_GROUNDS_DIR = TEXTURES_DIR + "grounds/";
	public static final String TEXTURES_SHADOWS_DIR = TEXTURES_DIR + "shadows/";

	public static BufferedImage loadImage(String folder, String filename) {
		try {
			return ImageIO.read(new File(folder + filename + ".png"));
		} catch (IOException e) {
			return null;
		}
	}

}
