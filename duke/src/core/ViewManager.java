package core;

import java.awt.Graphics2D;

public class ViewManager {

	private Core core;
	private int screenShiftX;
	private int screenShiftY;

	public ViewManager(Core core) {
		this.core = core;
	}

	public void shiftScreenX(int delta) {
		if (screenShiftX + delta < 0
				|| (screenShiftX + delta) / Settings.TILE_SIZE
						+ Settings.SCREEN_WIDTH_IN_TILES()
						- 3 > Settings.WORLD_WIDTH)
			return;
		screenShiftX += delta;
	}

	public void shiftScreenY(int delta) {
		if (screenShiftY + delta < 0
				|| (screenShiftY + delta) / Settings.TILE_SIZE
						+ Settings.SCREEN_HEIGHT_IN_TILES()
						- 3 > Settings.WORLD_HEIGHT)
			return;
		screenShiftY += delta;
	}

	public void draw(Graphics2D g) {
		drawWorld(g);
	}

	private void drawWorld(Graphics2D g) {
		World w = core.getWorld();
		int tileSize = Settings.TILE_SIZE;
		int screenWidth = Settings.GAME_FRAME_WIDTH / Settings.TILE_SIZE;
		int screenHeight = Settings.GAME_FRAME_HEIGHT / Settings.TILE_SIZE;

		int xstart = screenShiftX / Settings.TILE_SIZE - 1;
		int xrest = screenShiftX % Settings.TILE_SIZE;
		int ystart = screenShiftY / Settings.TILE_SIZE - 1;
		int yrest = screenShiftY % Settings.TILE_SIZE;

		for (int x = xstart; x < xstart + screenWidth + 2; x++) {
			for (int y = ystart; y < ystart + screenHeight + 2; y++) {
				w.getTile(x, y).draw(g, (x - xstart) * tileSize - xrest,
						(y - ystart) * tileSize - yrest);
			}
		}
	}

}
