package core;

public class Settings {

	public static final int SLEEP_TIME = 20;

	public static final int GAME_FRAME_XPOS = 0;
	public static final int GAME_FRAME_YPOS = 40;

	public static int GAME_FRAME_WIDTH;
	public static int GAME_FRAME_HEIGHT;

	public static int TILE_SIZE = 30;
	public static int WORLD_WIDTH = 100;
	public static int WORLD_HEIGHT = 100;

	public static int SCREEN_WIDTH_IN_TILES() {
		return Settings.GAME_FRAME_WIDTH / Settings.TILE_SIZE;
	}

	public static int SCREEN_HEIGHT_IN_TILES() {
		return Settings.GAME_FRAME_HEIGHT / Settings.TILE_SIZE;
	}

	public static int SHIFT_SPEED = 10;
}
