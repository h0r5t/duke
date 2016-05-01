package core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewManager {

	private Core core;
	private int currentZ = 0;
	private int screenShiftX;
	private int screenShiftY;
	private HashMap<Integer, ArrayList<Visual>> tileIdVisualsMap;

	public ViewManager(Core core) {
		this.core = core;
		tileIdVisualsMap = new HashMap<Integer, ArrayList<Visual>>();
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

	public void addVisualAt(Visual v, int x, int y, int z) {
		Tile t = Core.getWorld().getTile(x, y, z);
		ArrayList<Visual> list;
		if (!tileIdVisualsMap.containsKey(t.id())) {
			list = new ArrayList<Visual>();
			list.add(v);
			tileIdVisualsMap.put(t.id(), list);
		} else {
			list = tileIdVisualsMap.get(t.id());
			list.add(v);
			tileIdVisualsMap.put(t.id(), list);
		}
	}

	public void removeVisualAt(Visual v, int x, int y, int z) {
		Tile t = Core.getWorld().getTile(x, y, z);
		ArrayList<Visual> list = tileIdVisualsMap.get(t.id());
		list.remove(v);
		tileIdVisualsMap.put(t.id(), list);
	}

	public void shiftScreenX(int delta) {
		if (screenShiftX + delta < 0
				|| (screenShiftX + delta) / Settings.TILE_SIZE + Settings
						.SCREEN_WIDTH_IN_TILES() > Settings.WORLD_WIDTH + 2)
			return;
		screenShiftX += delta;
	}

	public void shiftScreenY(int delta) {
		if (screenShiftY + delta < 0
				|| (screenShiftY + delta) / Settings.TILE_SIZE + Settings
						.SCREEN_HEIGHT_IN_TILES() > Settings.WORLD_HEIGHT + 2)
			return;
		screenShiftY += delta;
	}

	public void draw(Graphics2D g) {
		drawWorld(g);
	}

	private void drawWorld(Graphics2D g) {
		World w = Core.getWorld();
		int tileSize = Settings.TILE_SIZE;
		int screenWidth = Settings.GAME_FRAME_WIDTH / Settings.TILE_SIZE + 1;
		int screenHeight = Settings.GAME_FRAME_HEIGHT / Settings.TILE_SIZE + 1;

		int xstart = screenShiftX / Settings.TILE_SIZE;
		int xrest = screenShiftX % Settings.TILE_SIZE;
		int ystart = screenShiftY / Settings.TILE_SIZE;
		int yrest = screenShiftY % Settings.TILE_SIZE;

		Cursor cursor = core.getInputManager().getCursor();

		int z = currentZ;
		for (int x = xstart; x < xstart + screenWidth + 2; x++) {
			for (int y = ystart; y < ystart + screenHeight + 2; y++) {
				w.getTile(x, y, z).draw(g, (x - xstart) * tileSize - xrest,
						(y - ystart) * tileSize - yrest);

				ArrayList<Visual> visualsList = tileIdVisualsMap
						.get(w.getTile(x, y, z).id());
				if (visualsList != null) {
					for (Visual v : visualsList) {
						v.draw(g, (x - xstart) * tileSize - xrest,
								(y - ystart) * tileSize - yrest);
					}
				}

				Unit u = w.getUnitAt(x, y, z);
				if (u != null) {
					u.draw(g, (x - xstart) * tileSize - xrest,
							(y - ystart) * tileSize - yrest);
				}

				if (cursor.getXpos() == x && cursor.getYpos() == y) {
					cursor.draw(g, (x - xstart) * tileSize - xrest,
							(y - ystart) * tileSize - yrest);
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
		return Core.getWorld().getTile(translateScreenPosToTilePosX(x),
				translateScreenPosToTilePosY(y), currentZ);
	}

}
