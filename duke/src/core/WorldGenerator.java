package core;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenerator {

	private Biome[][][] biomes;
	private World world;
	private Region region;
	private ArrayList<Coords3D> embarkArea;

	public WorldGenerator() {

	}

	public World generateWorld(Core core) {
		world = new World(core);
		biomes = new Biome[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT][Settings.WORLD_DEPTH];
		region = new RegionPlain();
		embarkArea = new ArrayList<>();

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
					biomes[x][y][z] = Biome.getBiome(Settings.WORLD_DEPTH - z, 0);
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
						tile = b.getSurfaceTile(x, y, z);
						tile.setGround(b.getGround());
						world.setTile(tile);
					} else {
						tile = new TileRock(x, y, z);
						world.setTile(tile);
					}
				}
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
		if (world.getTile(c.getLeft()).isSolid())
			if (!world.getTile(c.getLeft().getAbove()).isSolid())
				return Direction.LEFT;
		if (world.getTile(c.getRight()).isSolid())
			if (!world.getTile(c.getRight().getAbove()).isSolid())
				return Direction.RIGHT;
		if (world.getTile(c.getBottom()).isSolid())
			if (!world.getTile(c.getBottom().getAbove()).isSolid())
				return Direction.BOTTOM;
		if (world.getTile(c.getTop()).isSolid())
			if (!world.getTile(c.getTop().getAbove()).isSolid())
				return Direction.TOP;

		return null;
	}

	private static World generateSurface(World world) {
		Random r = new Random();
		int trees = r.nextInt(300) + 70;
		for (int i = 0; i < trees; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTile(new TileTree(x, y, 0));
		}

		int bushes = r.nextInt(200) + 50;
		for (int i = 0; i < bushes; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTile(new TileBush(x, y, 0));
		}

		int stones = r.nextInt(50) + 20;
		for (int i = 0; i < stones; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTile(new TileStone(x, y, 0));
		}

		int shrooms = r.nextInt(20) + 10;
		for (int i = 0; i < shrooms; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTile(new TileMushroom(x, y, 0));
		}

		return world;
	}

	private static World generateBaseTiles(World w) {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					if (z == 0)
						w.setTile(new TileLand(x, y, z));
					else {
						w.setTile(new TileRock(x, y, z));
					}
				}
			}
		}
		return w;
	}

}
