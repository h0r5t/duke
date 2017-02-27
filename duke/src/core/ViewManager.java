package core;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ViewManager {

	private Core core;
	private int currentZ = 0;
	private int screenShiftX;
	private int screenShiftY;

	public ViewManager(Core core) {
		this.core = core;
	}

	public void moveZ(int delta) {
		currentZ += delta;
		if (currentZ < 0) {
			currentZ = 0;
		}

		else if (currentZ > Settings.WORLD_DEPTH - 1) {
			currentZ = Settings.WORLD_DEPTH - 1;
		}
	}

	public int getCurrentZ() {
		return currentZ;
	}

	public void shiftScreenX(int delta) {
		if (screenShiftX + delta < 0 || (screenShiftX + delta) / Settings.TILE_SIZE
				+ Settings.SCREEN_WIDTH_IN_TILES() > Settings.WORLD_WIDTH + 1)
			return;
		screenShiftX += delta;
	}

	public void shiftScreenY(int delta) {
		if (screenShiftY + delta < 0 || (screenShiftY + delta) / Settings.TILE_SIZE
				+ Settings.SCREEN_HEIGHT_IN_TILES() > Settings.WORLD_HEIGHT + 2)
			return;
		screenShiftY += delta;
	}

	public void draw(Graphics2D g) {
		drawWorld(g);
		core.getMenuManager().draw(g);
	}

	private void drawWorld(Graphics2D g) {
		World w = Core.getWorld();
		int tileSize = Settings.TILE_SIZE;
		int screenWidth = Settings.SCREEN_WIDTH_IN_TILES();
		int screenHeight = Settings.SCREEN_HEIGHT_IN_TILES();

		int xstart = screenShiftX / Settings.TILE_SIZE;
		int xrest = screenShiftX % Settings.TILE_SIZE;
		int ystart = screenShiftY / Settings.TILE_SIZE;
		int yrest = screenShiftY % Settings.TILE_SIZE;
		int z = currentZ;

		Cursor cursor = core.getInputManager().getCursor();

		for (int x = xstart; x < xstart + screenWidth; x++) {
			for (int y = ystart; y < ystart + screenHeight; y++) {

				Tile tile = w.getTile(x, y, z);
				if (tile != null) {
					tile.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest);
				}

			}
		}

		for (int x = xstart; x < xstart + screenWidth; x++) {
			for (int y = ystart; y < ystart + screenHeight; y++) {
				Tile tile = w.getTile(x, y, z);

				if (tile != null && tile.isVisible()) {
					ArrayList<Item> items = w.getItemsAt(tile.getCoords3D());
					for (Item i : items) {
						i.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest);
					}
					ArrayList<Unit> units = w.getUnitsAt(x, y, z);
					if (units != null) {
						for (Unit u : units)
							u.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest);
					}
					ArrayList<Projectile> projectiles = tile.getProjectiles();
					for (Projectile p : projectiles)
						p.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest);
				}

				if (cursor.getXpos() == x && cursor.getYpos() == y) {
					cursor.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest);
				}
			}
		}

	}

	public int translateScreenPosToTilePosX(int x) {
		return (x + screenShiftX) / Settings.TILE_SIZE;
	}

	public int translateScreenPosToTilePosY(int y) {
		return (y + screenShiftY) / Settings.TILE_SIZE;
	}

	public Tile getTileFromScreenPos(int x, int y) {
		return Core.getWorld().getTile(translateScreenPosToTilePosX(x), translateScreenPosToTilePosY(y), currentZ);
	}

}
