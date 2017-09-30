package core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewManager {

	private Core core;
	private int currentZ = Settings.CAMERA_START_Z;
	private int screenShiftX;
	private int screenShiftY;
	private Sky sky;
	private HashMap<Position2D, Tile> darkerLevelsMap;

	public ViewManager(Core core) {
		this.core = core;
		this.sky = new Sky();
		this.darkerLevelsMap = new HashMap<>();
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

		g.drawImage(sky.getSkyImage(), -screenShiftX, -screenShiftY, null);

		for (int x = xstart; x < xstart + screenWidth; x++) {
			for (int y = ystart; y < ystart + screenHeight; y++) {

				// draw tiles
				Tile tile = w.getTile(x, y, z);
				if (tile != null && !(tile instanceof TileAir)) {
					tile.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, 0);
				}

				else if (tile instanceof TileAir) {
					boolean fluidFound = false;
					for (int i = 0; i < Settings.DRAW_DARKER_LEVELS_AMOUNT - 1; i++) {
						Tile tile2 = w.getTile(x, y, z + i + 1);
						if (tile2 != null && !(tile2.isAir()) && !(tile2 instanceof TileOOB)) {
							tile2.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, i + 1);
							darkerLevelsMap.put(new Position2D(x, y), tile2);
							break;
						}
						if (tile2 != null && (tile2 instanceof TileAir)) {
							if (tile2.hasFluid())
								fluidFound = true;

						}
					}
					if (fluidFound) {
						for (int i = 0; i < Settings.DRAW_DARKER_LEVELS_AMOUNT - 1; i++) {
							Tile tile2 = w.getTile(x, y, z + i + 1);
							if (tile2 != null && (tile2 instanceof TileAir) && tile2.hasFluid()) {
								tile2.getFluid().draw(g, (x - xstart) * tileSize - xrest,
										(y - ystart) * tileSize - yrest, i + 1);
								break;
							}
						}
					}

					if (tile.hasFluid()) {
						tile.getFluid().draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, 0);
					}
				}
			}
		}

		for (int x = xstart; x < xstart + screenWidth; x++) {
			for (int y = ystart; y < ystart + screenHeight; y++) {
				Tile tile = w.getTile(x, y, z);
				if (tile != null && !(tile instanceof TileAir)) {
					tile.drawBorders(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, 0);
				}

				else if (tile instanceof TileAir) {
					for (int i = 0; i < Settings.DRAW_DARKER_LEVELS_AMOUNT - 1; i++) {
						Tile tile2 = w.getTile(x, y, z + i + 1);
						if (tile2 != null && !(tile2 instanceof TileAir)) {
							tile2.drawBorders(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest,
									i + 1);
							break;
						}
					}
				}
			}
		}

		for (int x = xstart; x < xstart + screenWidth; x++) {
			for (int y = ystart; y < ystart + screenHeight; y++) {
				Tile tile = w.getTile(x, y, z);

				if (tile != null) {
					ArrayList<Unit> units = w.getUnitsAt(tile.getCoords3D());
					ArrayList<Item> items = w.getItemsAt(tile.getCoords3D());
					for (Item i : items) {
						if (i.isVisible()) {
							int moveDeltaX = 0;
							int moveDeltaY = 0;
							if (units != null) {
								for (Unit u : units) {
									if (u.getItemInHands() == i) {
										moveDeltaX = u.getCurrentMovementDeltas()[0];
										moveDeltaY = u.getCurrentMovementDeltas()[1];
									}
								}
							}

							i.draw(g, (x - xstart) * tileSize - xrest + moveDeltaX,
									(y - ystart) * tileSize - yrest + moveDeltaY, 0);
						}

					}
					if (units != null) {
						for (Unit u : units)
							u.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, 0);
					}
					ArrayList<Projectile> projectiles = tile.getProjectiles();
					for (Projectile p : projectiles) {
						p.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, 0);
					}

				}

				if (tile.isAir()) {
					for (int i = 0; i < Settings.DRAW_UNITS_DARKER_LEVELS_AMOUNT - 1; i++) {
						Tile tile2 = w.getTile(x, y, z + i + 1);
						if (tile2 != null && !(tile2 instanceof TileAir)) {

							ArrayList<Unit> units = w.getUnitsAt(tile2.getCoords3D());
							ArrayList<Item> items = w.getItemsAt(tile2.getCoords3D());
							for (Item item : items) {
								if (item.isVisible()) {
									int moveDeltaX = 0;
									int moveDeltaY = 0;
									if (units != null) {
										for (Unit u : units) {
											if (u.getItemInHands() == item) {
												moveDeltaX = u.getCurrentMovementDeltas()[0];
												moveDeltaY = u.getCurrentMovementDeltas()[1];
											}
										}
									}

									item.draw(g, (x - xstart) * tileSize - xrest + moveDeltaX,
											(y - ystart) * tileSize - yrest + moveDeltaY, i + 1);
								}

							}
							if (units != null) {
								for (Unit u : units)
									u.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, i + 1);
							}
							ArrayList<Projectile> projectiles = tile2.getProjectiles();
							for (Projectile p : projectiles) {
								p.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest, i + 1);
							}

							break;
						}
					}
				}

				if (cursor.getXpos() == x && cursor.getYpos() == y) {
					cursor.draw(g, (x - xstart) * tileSize - xrest, (y - ystart) * tileSize - yrest);
				}
			}
		}

		darkerLevelsMap.clear();

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

	private class Position2D {

		public int x;
		public int y;

		public Position2D(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return new String(x + "," + y).hashCode();
		}
	}
}
