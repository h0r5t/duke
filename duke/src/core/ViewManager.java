package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewManager {

	private Core core;
	private int currentZ = Settings.CAMERA_START_Z;
	private int screenShiftX;
	private int screenShiftY;
	private Sky sky;
	private HashMap<Position2D, Integer> darkerLevelsMap;
	private ArrayList<Shadow> shadowsToDraw;
	private ArrayList<DrawingOperation> drawingOperations;
	private DrawingOperation cursorDrawingOperation;

	public ViewManager(Core core) {
		this.core = core;
		this.sky = new Sky();
		this.darkerLevelsMap = new HashMap<>();
		this.shadowsToDraw = new ArrayList<>();
		this.drawingOperations = new ArrayList<>();
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

	private synchronized void drawWorld(Graphics2D g) {
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

				int drawX = (x - xstart) * tileSize - xrest;
				int drawY = (y - ystart) * tileSize - yrest;

				// surface
				Tile tile = w.getTile(x, y, z);
				if (tile == null)
					continue;

				if (!(tile instanceof TileAir)) {
					// tile
					tile.draw(g, drawX, drawY);

					// shadows
					checkForSurfaceShadows(tile, drawX, drawY);

					// units
					ArrayList<Unit> units = w.getUnitsAt(tile.getCoords3D());
					if (units != null) {
						for (Unit u : units) {
							// System.out.println("view " + x + "," + y + " " + drawX + "," + drawY);
							drawingOperations.add(new DrawingOperation(u, drawX, drawY));
						}
					}

					// items
					ArrayList<Item> items = w.getItemsAt(tile.getCoords3D());
					for (Item i : items) {
						if (i.isVisible()) {
							drawingOperations.add(new DrawingOperation(i, drawX, drawY));
						}
					}

					// projectiles
					ArrayList<Projectile> projectiles = tile.getProjectiles();
					for (Projectile p : projectiles) {
						drawingOperations.add(new DrawingOperation(p, drawX, drawY));
					}
				}

				// deep
				else {
					if (tile.hasFluid()) {
						drawingOperations.add(new DrawingOperation(tile.getFluid(), drawX, drawY));
					}
					for (int i = 0; i < Settings.DRAW_DARKER_LEVELS_AMOUNT - 1; i++) {
						Tile tile2 = w.getTile(x, y, z + i + 1);
						if (tile == null)
							continue;
						if (!(tile2.isAir()) && !(tile2 instanceof TileOOB)) {
							// tile
							tile2.draw(g, drawX, drawY);

							// darker levels
							addToDarkerLevelsMap(new Position2D(drawX, drawY), i + 1);

							// shadows
							checkForDeepShadows(tile2, drawX, drawY);

							// units
							ArrayList<Unit> units = w.getUnitsAt(tile2.getCoords3D());
							if (units != null) {
								for (Unit u : units)
									drawingOperations.add(new DrawingOperation(u, drawX, drawY));
							}

							// items
							ArrayList<Item> items = w.getItemsAt(tile2.getCoords3D());
							for (Item item : items) {
								if (item.isVisible()) {
									drawingOperations.add(new DrawingOperation(item, drawX, drawY));
								}
							}

							// projectiles
							ArrayList<Projectile> projectiles = tile2.getProjectiles();
							for (Projectile p : projectiles) {
								drawingOperations.add(new DrawingOperation(p, drawX, drawY));
							}

							break;
						}

						// draw everything that can also be in the air (fluid, projectiles, units?)
						if (tile2 instanceof TileAir) {
							if (tile2.hasFluid()) {
								drawingOperations.add(new DrawingOperation(tile2.getFluid(), drawX, drawY));
								addToDarkerLevelsMap(new Position2D(drawX, drawY), i + 1);
							}
						}
					}

				}

				if (cursor.getXpos() == x && cursor.getYpos() == y) {
					cursorDrawingOperation = new DrawingOperation(cursor, drawX, drawY);
				}
			}
		}

		for (DrawingOperation op : drawingOperations) {
			op.draw(g);
		}

		int changeAlphaDelta = 200 / Settings.DRAW_DARKER_LEVELS_AMOUNT;
		for (Position2D p : darkerLevelsMap.keySet()) {
			Integer darkerLevel = darkerLevelsMap.get(p);

			int delta = darkerLevel * changeAlphaDelta;
			Color color = new Color(0, 0, 0, delta);
			g.setColor(color);
			g.fillRect(p.x, p.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
		}

		for (Shadow s : shadowsToDraw) {
			s.draw(g);
		}

		cursorDrawingOperation.draw(g);

		drawingOperations.clear();
		darkerLevelsMap.clear();
		shadowsToDraw.clear();
	}

	private void checkForSurfaceShadows(Tile tile, int drawX, int drawY) {
		if (tile.getLeft() instanceof TileAir) {
			addShadowDrawingOperation(Direction.RIGHT, drawX - Settings.TILE_SIZE, drawY);
			if (tile.getLeft().getBottom() instanceof TileAir)
				addShadowDrawingOperation(Direction.TOP_RIGHT, drawX - Settings.TILE_SIZE, drawY + Settings.TILE_SIZE);
			if (tile.getLeft().getTop() instanceof TileAir)
				addShadowDrawingOperation(Direction.BOTTOM_RIGHT, drawX - Settings.TILE_SIZE,
						drawY - Settings.TILE_SIZE);
		}
		if (tile.getRight() instanceof TileAir) {
			addShadowDrawingOperation(Direction.LEFT, drawX + Settings.TILE_SIZE, drawY);
			if (tile.getRight().getBottom() instanceof TileAir)
				addShadowDrawingOperation(Direction.TOP_LEFT, drawX + Settings.TILE_SIZE, drawY + Settings.TILE_SIZE);
			if (tile.getRight().getTop() instanceof TileAir)
				addShadowDrawingOperation(Direction.BOTTOM_LEFT, drawX + Settings.TILE_SIZE,
						drawY - Settings.TILE_SIZE);
		}
		if (tile.getTop() instanceof TileAir) {
			addShadowDrawingOperation(Direction.BOTTOM, drawX, drawY - Settings.TILE_SIZE);
		}
		if (tile.getBottom() instanceof TileAir) {
			addShadowDrawingOperation(Direction.TOP, drawX, drawY + Settings.TILE_SIZE);
		}
	}

	private void checkForDeepShadows(Tile tile, int drawX, int drawY) {
		Tile shadowTile = tile.getCoords3D().getAbove().getTile();

		boolean airLeft = false;
		boolean airRight = false;
		boolean airBottom = false;
		boolean airTop = false;

		if (!(shadowTile.getLeft() instanceof TileAir)) {
			addShadowDrawingOperation(Direction.LEFT, drawX, drawY);
		} else
			airLeft = true;
		if (!(shadowTile.getRight() instanceof TileAir)) {
			addShadowDrawingOperation(Direction.RIGHT, drawX, drawY);
		} else
			airRight = true;
		if (!(shadowTile.getBottom() instanceof TileAir)) {
			addShadowDrawingOperation(Direction.BOTTOM, drawX, drawY);
		} else
			airBottom = true;
		if (!(shadowTile.getTop() instanceof TileAir)) {
			addShadowDrawingOperation(Direction.TOP, drawX, drawY);
		} else
			airTop = true;

		if (airLeft) {
			if (airTop) {
				if (!shadowTile.getLeft().getTop().isAir()) {
					addShadowDrawingOperation(Direction.TOP_LEFT, drawX, drawY);
				}
			}

			if (airBottom) {
				if (!shadowTile.getLeft().getBottom().isAir()) {
					addShadowDrawingOperation(Direction.BOTTOM_LEFT, drawX, drawY);
				}
			}
		}

		if (airRight) {
			if (airTop) {
				if (!shadowTile.getRight().getTop().isAir()) {
					addShadowDrawingOperation(Direction.TOP_RIGHT, drawX, drawY);
				}
			}

			if (airBottom) {
				if (!shadowTile.getRight().getBottom().isAir()) {
					addShadowDrawingOperation(Direction.BOTTOM_RIGHT, drawX, drawY);
				}
			}
		}
	}

	private void addShadowDrawingOperation(Direction d, int x, int y) {
		shadowsToDraw.add(new Shadow(d, x, y));
	}

	private void addToDarkerLevelsMap(Position2D p, Integer value) {
		if (darkerLevelsMap.containsKey(p)) {
			int currentVal = (Integer) darkerLevelsMap.get(p);
			if (value < currentVal)
				darkerLevelsMap.put(p, value);
		} else
			darkerLevelsMap.put(p, value);
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
