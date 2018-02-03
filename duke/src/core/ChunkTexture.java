package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ChunkTexture implements Drawable {

	private int x;
	private int y;
	private int z;
	private BufferedImage texture;
	private ArrayList<Shadow> shadowsToDraw;
	private HashMap<Coords2D, Integer> darkerLevelsMap;
	private static ChunkTextureMock textureMock = new ChunkTextureMock();

	public ChunkTexture(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration config = device.getDefaultConfiguration();

		texture = config.createCompatibleImage(Settings.TILE_SIZE * Settings.CHUNK_SIZE,
				Settings.TILE_SIZE * Settings.CHUNK_SIZE);

		this.shadowsToDraw = new ArrayList<>();
		this.darkerLevelsMap = new HashMap<>();
	}

	public void renderWhole() {
		Graphics2D g = (Graphics2D) texture.getGraphics();
		World w = Core.getWorld();

		g.setColor(Colors.COLOR_SKY);
		g.fillRect(0, 0, Settings.CHUNK_SIZE * Settings.TILE_SIZE, Settings.CHUNK_SIZE * Settings.TILE_SIZE);

		for (int a = 0; a < Settings.CHUNK_SIZE; a++) {
			for (int b = 0; b < Settings.CHUNK_SIZE; b++) {
				int drawX = a * Settings.TILE_SIZE;
				int drawY = b * Settings.TILE_SIZE;

				int tileX = a + x;
				int tileY = b + y;

				Tile tile = w.getTile(new Coords3D(tileX, tileY, z));

				if (tile instanceof TileOOB) {
					tile.draw(g, drawX, drawY);
					continue;
				}

				if (!(tile instanceof TileAir)) {
					tile.draw(g, drawX, drawY);
					checkForSurfaceShadows(tile, drawX, drawY);
					darkerLevelsMap.put(new Coords2D(drawX, drawY), 0);
				}

				else {
					for (int i = 0; i < Settings.DRAW_DARKER_LEVELS_AMOUNT - 1; i++) {
						Tile tile2 = w.getTile(tileX, tileY, z + i + 1);
						if (tile == null)
							continue;
						if (!(tile2.isAir()) && !(tile2 instanceof TileOOB)) {
							tile2.draw(g, drawX, drawY);
							checkForDeepShadows(tile2, drawX, drawY);
							darkerLevelsMap.put(new Coords2D(drawX, drawY), i + 1);
							break;
						}
					}
				}
			}
		}

		// render shadows on top
		for (Shadow shadow : shadowsToDraw)
			shadow.draw(g, shadow.getDrawX(), shadow.getDrawY());

		// render darknessLevels
		for (Coords2D p : darkerLevelsMap.keySet()) {
			int darkerLevel = darkerLevelsMap.get(p);
			TextureStore.getLevelDarknessTexture(darkerLevel).draw(g, p.getX(), p.getY());
		}

		// render chunk borders
		if (Settings.RENDER_CHUNK_BORDERS) {
			g.setColor(Color.RED);
			g.drawRect(0, 0, texture.getWidth(), texture.getHeight());
		}

		shadowsToDraw.clear();
		darkerLevelsMap.clear();
		g.dispose();

		// make a compatible image, maybe it helps...
		texture = Texture.makeCompatible(texture);
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

	public static ChunkTextureMock getTextureMock() {
		return textureMock;
	}

	private void addShadowDrawingOperation(Direction d, int x, int y) {
		shadowsToDraw.add(new Shadow(d, x, y));
	}

	@Override
	public void draw(Graphics2D g, int drawX, int drawY) {
		g.drawImage(texture, drawX, drawY, null);
	}

}
