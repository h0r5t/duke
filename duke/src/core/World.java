package core;

import java.util.HashMap;

import pathfinder.Graph;

public class World extends Graph {

	private int[][][] world;
	private HashMap<Integer, Unit> tileIdUnitMap;

	public World() {
		super();
		world = new int[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT][Settings.WORLD_DEPTH];
		tileIdUnitMap = new HashMap<Integer, Unit>();
	}

	public void addUnit(Unit u) {
		tileIdUnitMap.put(getTile(u.getX(), u.getY(), u.getZ()).id(), u);
	}

	public void removeUnit(Unit u) {
		tileIdUnitMap.remove(getTile(u.getX(), u.getY(), u.getZ()).id(), u);
	}

	public void moveUnit(Unit u, int oldX, int oldY, int oldZ, int newX,
			int newY, int newZ) {
		tileIdUnitMap.remove(getTile(oldX, oldY, oldZ).id(), u);
		tileIdUnitMap.put(getTile(newX, newY, newZ).id(), u);
	}

	public void moveUnit(Unit u, Tile from, Tile to) {
		tileIdUnitMap.remove(from.id(), u);
		tileIdUnitMap.remove(to.id(), u);
	}

	public Unit getUnitAt(Tile tile) {
		return tileIdUnitMap.get(tile.id());
	}

	public Unit getUnitAt(int x, int y, int z) {
		return tileIdUnitMap.get(getTile(x, y, z).id());
	}

	public void setTile(Tile tile) {
		int x = tile.getX();
		int y = tile.getY();
		int z = tile.getZ();

		removeNode(world[x][y][z]);
		world[x][y][z] = tile.id();
		addNode(tile);

		updateXYEdgesForTile(x, y, z);
		updateXYEdgesForTile(x - 1, y, z);
		updateXYEdgesForTile(x + 1, y, z);
		updateXYEdgesForTile(x, y - 1, z);
		updateXYEdgesForTile(x, y + 1, z);

		if (tile.isLadderDown()) {
			addZEdgesForTile(x, y, z);
		}
		if (tile.isLadderUp()) {
			addZEdgesForTile(x, y, z);
		}
	}

	public Tile getTile(int x, int y, int z) {
		if (x < 0 || x >= world.length || y < 0 || y >= world[0].length)
			return new TileOOB(x, y, z);
		return (Tile) getNode(world[x][y][z]);
	}

	public int getWidth() {
		return world.length;
	}

	public int getHeight() {
		return world[0].length;
	}

	public void updateXYEdgesForTile(int x, int y, int z) {
		if (x < 0 || x >= world.length || y < 0 || y >= world[0].length)
			return;

		if (getTile(x, y, z).collides()) {
			return;
		}

		if (x > 0) {
			if (!getTile(x - 1, y, z).collides()) {
				addEdge(world[x][y][z], world[x - 1][y][z], 0);
			} else {
				try {
					removeEdge(world[x][y][z], world[x - 1][y][z]);
				} catch (Exception e) {
				}
			}
		}
		if (x < Settings.WORLD_WIDTH - 1) {
			if (!getTile(x + 1, y, z).collides()) {
				addEdge(world[x][y][z], world[x + 1][y][z], 0);
			} else {
				try {
					removeEdge(world[x][y][z], world[x + 1][y][z]);
				} catch (Exception e) {
				}
			}
		}
		if (y > 0) {
			if (!getTile(x, y - 1, z).collides()) {
				addEdge(world[x][y][z], world[x][y - 1][z], 0);
			} else {
				try {
					removeEdge(world[x][y][z], world[x][y - 1][z]);
				} catch (Exception e) {
				}
			}
		}
		if (y < Settings.WORLD_HEIGHT - 1) {
			if (!getTile(x, y + 1, z).collides()) {
				addEdge(world[x][y][z], world[x][y + 1][z], 0);
			} else {
				try {
					removeEdge(world[x][y][z], world[x][y + 1][z]);
				} catch (Exception e) {
				}
			}
		}
	}

	public void addZEdgesForTile(int x, int y, int z) {
		Tile tile = getTile(x, y, z);

		if (tile.isLadderDown()) {
			if (z < Settings.WORLD_DEPTH - 1) {
				addEdge(world[x][y][z], world[x][y][z + 1], 0);
			}
		}

		if (tile.isLadderUp()) {
			if (z > 0) {
				addEdge(world[x][y][z], world[x][y][z - 1], 0);
			}
		}
	}

	public void createInitialEdges() {
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					updateXYEdgesForTile(x, y, z);
					if (getTile(x, y, z).isLadderDown()
							|| getTile(x, y, z).isLadderUp())
						addZEdgesForTile(x, y, z);
				}
			}
		}
	}

	public static World generateWorld() {
		World w = new World();
		for (int i = 0; i < Settings.WORLD_WIDTH; i++) {
			for (int o = 0; o < Settings.WORLD_HEIGHT; o++) {
				for (int a = 0; a < Settings.WORLD_DEPTH; a++) {
					w.setTileINITIAL(new TileLand(i, o, a));
				}
			}
		}
		w.setTileINITIAL(new TileWoods(10, 10, 0));
		w.setTileINITIAL(new TileLadderDown(7, 5, 0));
		w.setTileINITIAL(new TileLadderUp(7, 5, 1));
		w.createInitialEdges();
		return w;
	}

	public void setTileINITIAL(Tile tile) {
		world[tile.getX()][tile.getY()][tile.getZ()] = tile.id();
		addNode(tile);
	}

}
