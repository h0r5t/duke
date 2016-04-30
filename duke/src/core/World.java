package core;

import java.util.HashMap;

import pathfinder.Graph;

public class World extends Graph {

	private int[][] world;
	private HashMap<Integer, Unit> tileIdUnitMap;

	public World() {
		super();
		world = new int[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT];
		tileIdUnitMap = new HashMap<Integer, Unit>();
	}

	public void addUnit(Unit u) {
		tileIdUnitMap.put(getTile(u.getX(), u.getY()).id(), u);
	}

	public void removeUnit(Unit u) {
		tileIdUnitMap.remove(getTile(u.getX(), u.getY()).id(), u);
	}

	public void moveUnit(Unit u, int oldX, int oldY, int newX, int newY) {
		tileIdUnitMap.remove(getTile(oldX, oldY).id(), u);
		tileIdUnitMap.put(getTile(newX, newY).id(), u);
	}

	public Unit getUnitAt(Tile tile) {
		return tileIdUnitMap.get(tile.id());
	}

	public Unit getUnitAt(int x, int y) {
		return tileIdUnitMap.get(getTile(x, y).id());
	}

	public void setTile(Tile tile) {
		int x = tile.getX();
		int y = tile.getY();

		removeNode(world[x][y]);
		world[x][y] = tile.id();
		addNode(tile);

		updateEdgesForTile(x, y);
		updateEdgesForTile(x - 1, y);
		updateEdgesForTile(x + 1, y);
		updateEdgesForTile(x, y - 1);
		updateEdgesForTile(x, y + 1);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= world.length || y < 0 || y >= world[0].length)
			return new TileOOB(x, y);
		return (Tile) getNode(world[x][y]);
	}

	public int getWidth() {
		return world.length;
	}

	public int getHeight() {
		return world[0].length;
	}

	public void updateEdgesForTile(int x, int y) {
		if (x < 0 || x >= world.length || y < 0 || y >= world[0].length)
			return;

		if (getTile(x, y).collides()) {
			return;
		}

		if (x > 0) {
			if (!getTile(x - 1, y).collides()) {
				addEdge(world[x][y], world[x - 1][y], 0);
			} else {
				try {
					removeEdge(world[x][y], world[x - 1][y]);
				} catch (Exception e) {
				}
			}
		}
		if (x < Settings.WORLD_WIDTH - 1) {
			if (!getTile(x + 1, y).collides()) {
				addEdge(world[x][y], world[x + 1][y], 0);
			} else {
				try {
					removeEdge(world[x][y], world[x + 1][y]);
				} catch (Exception e) {
				}
			}
		}
		if (y > 0) {
			if (!getTile(x, y - 1).collides()) {
				addEdge(world[x][y], world[x][y - 1], 0);
			} else {
				try {
					removeEdge(world[x][y], world[x][y - 1]);
				} catch (Exception e) {
				}
			}
		}
		if (y < Settings.WORLD_HEIGHT - 1) {
			if (!getTile(x, y + 1).collides()) {
				addEdge(world[x][y], world[x][y + 1], 0);
			} else {
				try {
					removeEdge(world[x][y], world[x][y + 1]);
				} catch (Exception e) {
				}
			}
		}
	}

	public void createInitialEdges() {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				updateEdgesForTile(x, y);
			}
		}
	}

	public static World generateWorld() {
		World w = new World();
		for (int i = 0; i < Settings.WORLD_WIDTH; i++) {
			for (int o = 0; o < Settings.WORLD_HEIGHT; o++) {
				w.setTileINITIAL(new TileLand(i, o));
			}
		}
		w.setTileINITIAL(new TileWoods(10, 10));
		w.createInitialEdges();
		return w;
	}

	public void setTileINITIAL(Tile tile) {
		world[tile.getX()][tile.getY()] = tile.id();
		addNode(tile);
	}

}
