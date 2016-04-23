package core;

public class World {

	private Tile[][] world;

	public World() {
		world = new Tile[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT];
	}

	public void setTile(int x, int y, Tile tile) {
		world[x][y] = tile;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= world.length || y < 0 || y >= world[0].length)
			return new TileOOB();
		return world[x][y];
	}

	public int getWidth() {
		return world.length;
	}

	public int getHeight() {
		return world[0].length;
	}

	public static World generateWorld() {
		World w = new World();
		for (int i = 0; i < Settings.WORLD_WIDTH; i++) {
			for (int o = 0; o < Settings.WORLD_HEIGHT; o++) {
				w.setTile(i, o, new TileLand());
			}
		}
		w.setTile(10, 10, new TileWoods());
		return w;
	}

}
