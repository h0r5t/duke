package core;

import java.util.Random;

public class WorldGenerator {

	public static World generateWorld() {
		World world = new World();
		world = generateBaseTiles(world);
		world = generateSurface(world);

		world.createInitialEdges();
		return world;
	}

	private static World generateSurface(World world) {
		Random r = new Random();
		int trees = r.nextInt(300) + 70;
		for (int i = 0; i < trees; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);
			world.setTileINITIAL(new TileWoods(x, y, 0));
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

		int lakes = r.nextInt(3) + 1;
		for (int i = 0; i < lakes; i++) {
			int x = r.nextInt(Settings.WORLD_WIDTH);
			int y = r.nextInt(Settings.WORLD_HEIGHT);

			int width = r.nextInt(10) + 5;
			int height = r.nextInt(10) + 5;
			for (int a = 0; a < width; a++) {
				for (int b = 0; b < height; b++) {
					if (a == 0 || a == width - 1 || b == 0 || b == height - 1) {
						int c = r.nextInt(5);
						if (c > 2)
							world.setTileINITIAL(
									new TileWater(x + a, y + b, 0));
					} else
						world.setTileINITIAL(new TileWater(x + a, y + b, 0));
				}
			}

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
