package core;

public class Settings {

	public static final int SLEEP_TIME = 50;

	public static final int GAME_FRAME_XPOS = 0;
	public static final int GAME_FRAME_YPOS = 40;

	public static int GAME_FRAME_WIDTH;
	public static int GAME_FRAME_HEIGHT;

	public static int TILE_SIZE = 20;
	public static int CHAR_FONT_SIZE = 26;

	public static int WORLD_DEPTH = 30;
	public static int WORLD_WIDTH = 100;
	public static int WORLD_HEIGHT = 50;

	public static int TEXTURE_COLOR_VARIATION = 5;
	public static int TEXTURE_ALPHA_VARIATION = 0;
	public static int TEXTURE_SECTION_SIZE = 5;
	public static boolean DRAW_TILE_BORDERS = false;

	public static int MENU_WIDTH = 300;

	public static int MENU_STARTX() {
		return Settings.GAME_FRAME_WIDTH - MENU_WIDTH - 5;
	}

	public static int SCREEN_WIDTH_IN_TILES() {
		return (Settings.GAME_FRAME_WIDTH - MENU_WIDTH) / Settings.TILE_SIZE;
	}

	public static int SCREEN_HEIGHT_IN_TILES() {
		return Settings.GAME_FRAME_HEIGHT / Settings.TILE_SIZE;
	}

	public static int SHIFT_SPEED = 20;
}
