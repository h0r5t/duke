package core;

import java.util.ArrayList;

public class WorldGenerator {

	private Biome[][][] biomes;
	private World world;
	private Region region;
	private ArrayList<Coords3D> embarkArea;
	private int[][] surfaceLevel;

	public WorldGenerator() {
	}

	public World generateWorld(Core core) {
		world = new World(core);
		biomes = new Biome[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT][Settings.WORLD_DEPTH];
		region = new RegionHills();
		embarkArea = new ArrayList<>();
		surfaceLevel = new int[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT];

		generateWorld(world);

		world.createInitialEdges();
		return world;
	}

	private void generateWorld(World world) {
		generateTerrain();
		generateBiomes();
		generateRamps();

		applyVision();
		setEmbarkArea();
	}

	private void generateRamps() {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					Tile tile = world.getTile(x, y, z);
					if (tile instanceof TileAir)
						continue;
					if (isSurface(tile)) {
						Direction direction = isLadderUp(tile);
						if (direction != null) {
							Biome b = biomes[x][y][z];
							tile = new TileRamp(x, y, z, direction);
							tile.setGround(b.getGround());
							world.setTile(tile);
						}
					}
				}
			}
		}
	}

	private void setEmbarkArea() {
		int width = 4;
		int height = 4;
		int xstart = 25;
		int ystart = 25;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					Tile tile = world.getTile(xstart + x, ystart + y, z);
					if (isSurface(tile)) {
						embarkArea.add(tile.getCoords3D());
						break;
					}
				}
			}
		}
		PathFinder.setReachablePoint(embarkArea.get(0));
	}

	private void generateTerrain() {
		int width = Settings.WORLD_WIDTH;
		int height = Settings.WORLD_HEIGHT;
		int[][] heightMap = region.getHeightMap(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					int h = heightMap[x][y];
					int currentHeight = Settings.WORLD_DEPTH - z;

					if (currentHeight <= h) {
						world.setTile(new TileRock(x, y, z));
					} else
						world.setTile(new TileAir(x, y, z));
				}
			}
		}
	}

	private void generateBiomes() {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					if (world.getTile(x, y, z) instanceof TileAir)
						continue;
					biomes[x][y][z] = Biome.getBiome(this, Settings.WORLD_DEPTH - z, 0);
				}
			}
		}

		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					Tile tile = world.getTile(x, y, z);
					if (tile instanceof TileAir)
						continue;
					if (isSurface(tile)) {
						Biome b = biomes[x][y][z];
						surfaceLevel[x][y] = z;
						tile.setGround(b.getGround());
						world.setTile(tile);
					} else {
						tile = new TileRock(x, y, z);
						world.setTile(tile);
					}
				}
			}
		}

		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				int z = surfaceLevel[x][y];
				Biome b = biomes[x][y][z];
				Tile t = b.getDetailTile(x, y, z);
				t.setGround(b.getGround());
				world.setTile(t);
			}
		}
	}

	private void applyVision() {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					Tile tile = world.getTile(x, y, z);
					if (tile instanceof TileAir)
						continue;

					if (isExposed(tile)) {
						tile.setExposed(true);
					}
				}
			}
		}
	}

	private boolean isInWorldBounds(int x, int y) {
		if (x < 0 || x > Settings.WORLD_WIDTH - 1 || y < 0 || y > Settings.WORLD_HEIGHT - 1)
			return false;
		return true;
	}

	private boolean isSurface(Tile t) {
		Coords3D c = t.getCoords3D();
		if (!t.isAir() && world.getTile(c.getAbove()).isAir()) {
			return true;
		}
		return false;
	}

	public ArrayList<Coords3D> getEmbarkArea() {
		return embarkArea;
	}

	private boolean isExposed(Tile t) {
		Coords3D c = t.getCoords3D();
		if (!world.getTile(c.getLeft()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getRight()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getTop()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getBottom()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getBottomLeft()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getBottomRight()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getTopLeft()).isSolid()) {
			return true;
		}
		if (!world.getTile(c.getTopRight()).isSolid()) {
			return true;
		}
		if (world.getTile(c.getAbove()).isAir()) {
			return true;
		}
		return false;
	}

	private Direction isLadderUp(Tile t) {
		Coords3D c = t.getCoords3D();
		if (world.getTile(c.getLeft()).needsRamp())
			if (!world.getTile(c.getLeft().getAbove()).isSolid())
				return Direction.LEFT;
		if (world.getTile(c.getRight()).needsRamp())
			if (!world.getTile(c.getRight().getAbove()).isSolid())
				return Direction.RIGHT;
		if (world.getTile(c.getBottom()).needsRamp())
			if (!world.getTile(c.getBottom().getAbove()).isSolid())
				return Direction.BOTTOM;
		if (world.getTile(c.getTop()).needsRamp())
			if (!world.getTile(c.getTop().getAbove()).isSolid())
				return Direction.TOP;

		return null;
	}

	public int getSurfaceLevel(int x, int y) {
		return surfaceLevel[x][y];
	}

}
