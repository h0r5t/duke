package core;

public class Settings {

	public static final int TICK_TIME = 50;

	public static final int GAME_FRAME_XPOS = 0;
	public static final int GAME_FRAME_YPOS = 40;

	public static int GAME_FRAME_WIDTH;
	public static int GAME_FRAME_HEIGHT;

	public static int TILE_SIZE = 25;
	public static int CHAR_FONT_SIZE = 30;

	public static int WORLD_DEPTH = 50;
	public static int WORLD_WIDTH = 100;
	public static int WORLD_HEIGHT = 50;

	public static boolean DRAW_TILE_BORDERS = false;

	public static int MENU_HEIGHT = 75;
	public static int MENU_GROUP_WIDTH = 150;
	public static int MENU_ELEMENT_HEIGHT = 30;

	public static int MENU_STARTY() {
		return Settings.GAME_FRAME_HEIGHT - MENU_HEIGHT - 5;
	}

	public static int SCREEN_WIDTH_IN_TILES() {
		return (Settings.GAME_FRAME_WIDTH) / Settings.TILE_SIZE + 1;
	}

	public static int SCREEN_HEIGHT_IN_TILES() {
		return (Settings.GAME_FRAME_HEIGHT - MENU_HEIGHT) / Settings.TILE_SIZE
				+ 2;
	}

	public static int SHIFT_SPEED = 20;
}
