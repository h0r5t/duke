package core;

import java.util.Random;

public class WorldGenerator {

	private static Biome[][][] biomes;

	public static World generateWorld(Core core) {
		World world = new World(core);
		biomes = new Biome[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT][Settings.WORLD_DEPTH];

		generateWorld(world);

		world.createInitialEdges();
		return world;
	}

	private static void generateWorld(World world) {
		generateTerrain(world);
		generateBiomes(world);
	}

	private static void generateTerrain(World world) {
		int width = Settings.WORLD_WIDTH;
		int height = Settings.WORLD_HEIGHT;
		int[][] heightMap = getHeightMap(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					int h = heightMap[x][y];
					int currentHeight = Settings.WORLD_DEPTH - z;
					if (currentHeight <= h) {
						world.setTileINITIAL(new TileRock(x, y, z));
					} else
						world.setTileINITIAL(new TileAir(x, y, z));
				}
			}
		}
	}

	private static void generateBiomes(World world) {
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
					if (world.getTile(x, y, z) instanceof TileAir)
						continue;
					world.getTile(x, y, z).setGround(biomes[x][y][z].getGround());
				}
			}
		}
	}

	private static int[][] getHeightMap(int width, int height) {
		SimplexNoise noise = new SimplexNoise(100, 0.4, (int) (Math.random() * 5000));
		int[][] values = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double val = noise.getNoise(x, y);

				val = val * 20 + 30;

				int z = (int) val;
				values[x][y] = z;
			}
		}

		return values;
	}

	private static int[][] getMoistureMap(int width, int height) {
		SimplexNoise noise = new SimplexNoise(30, 0.7, (int) (Math.random() * 5000));
		int[][] values = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double val = noise.getNoise(x, y);
				val = (val + 1.0) / 2.0;
				values[x][y] = (int) val;
			}
		}

		return values;
	}

	private static World generateSurface(World world) {
		Random r = new Random();
		int trees = r.nextInt(300) + 70;
		for (int i = 0; i < trees; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTileINITIAL(new TileTree(x, y, 0));
		}

		int bushes = r.nextInt(200) + 50;
		for (int i = 0; i < bushes; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTileINITIAL(new TileBush(x, y, 0));
		}

		int stones = r.nextInt(50) + 20;
		for (int i = 0; i < stones; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTileINITIAL(new TileStone(x, y, 0));
		}

		int shrooms = r.nextInt(20) + 10;
		for (int i = 0; i < shrooms; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTileINITIAL(new TileMushroom(x, y, 0));
		}

		return world;
	}

	private static World generateBaseTiles(World w) {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					if (z == 0)
						w.setTileINITIAL(new TileLand(x, y, z));
					else {
						w.setTileINITIAL(new TileRock(x, y, z));
					}
				}
			}
		}
		return w;
	}

}
