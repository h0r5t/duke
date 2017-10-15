package core;

public class Settings {

	public static final long TICK_TIME = 10;

	public static final int GAME_FRAME_XPOS = 0;
	public static final int GAME_FRAME_YPOS = 40;

	public static final boolean DRAW_BORDERS = true;

	public static int GAME_FRAME_WIDTH;
	public static int GAME_FRAME_HEIGHT;

	public static int TILE_SIZE = 20;

	public static int WORLD_DEPTH = 50;
	public static int WORLD_WIDTH = 200;
	public static int WORLD_HEIGHT = 50;

	public static final int CHUNKLOADER_THREAD_AMOUNT = 10;

	public static final int CHUNK_SIZE = 20;
	public static final int CHUNK_DEPTH = 5;
	public static final int CHUNK_SCOPE_OVERLAP_X = 1;
	public static final int CHUNK_SCOPE_OVERLAP_Y = 1;
	public static final int CHUNK_SCOPE_OVERLAP_Z = 1;

	public static final boolean RENDER_CHUNK_BORDERS = false;

	public static int CHUNK_SCOPE_WIDTH;
	public static int CHUNK_SCOPE_HEIGHT;
	public static int CHUNK_SCOPE_DEPTH;

	public static int OVERWORLD_DEPTH = 40;
	public static int CAMERA_START_Z = 1;

	public static boolean DRAW_TILE_BORDERS = false;

	public static int DRAW_DARKER_LEVELS_AMOUNT = 10;
	public static int DRAW_UNITS_DARKER_LEVELS_AMOUNT = DRAW_DARKER_LEVELS_AMOUNT;

	public static int MENU_HEIGHT = 75;
	public static int MENU_WIDTH = 400;
	public static int MENU_ELEMENT_HEIGHT = 30;

	public static int MENU_STARTX() {
		return Settings.GAME_FRAME_WIDTH - MENU_WIDTH;
	}

	public static int MENU_STARTY() {
		return Settings.GAME_FRAME_HEIGHT - MENU_HEIGHT - 5;
	}

	public static int SCREEN_WIDTH_IN_TILES() {
		return (Settings.GAME_FRAME_WIDTH - MENU_WIDTH) / Settings.TILE_SIZE;
	}

	public static int SCREEN_HEIGHT_IN_TILES() {
		return (Settings.GAME_FRAME_HEIGHT) / Settings.TILE_SIZE;
	}

	public static int SHIFT_SPEED = 10;
}
