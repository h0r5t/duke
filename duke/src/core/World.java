package core;

import pathfinder.Graph;

public class World extends Graph {

	private Core core;
	private int[][][] world;

	public World(Core core) {
		super();
		this.core = core;
		world = new int[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT][Settings.WORLD_DEPTH];
	}

	public Unit getUnitAt(Tile tile) {
		return core.getUnitManager().getUnitAt(tile);
	}

	public Unit getUnitAt(int x, int y, int z) {
		return core.getUnitManager().getUnitAt(x, y, z);
	}

	public void setTile(Tile tile) {
		if (tile.isLadderDown() || tile.isLadderUp()) {
			setLadder(tile);
			return;
		}

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

		if (getTile(x, y, z).isVisible()) {
			getTile(x + 1, y, z).setVisible(true);
			getTile(x + -1, y, z).setVisible(true);

			getTile(x, y + 1, z).setVisible(true);
			getTile(x, y - 1, z).setVisible(true);

			getTile(x + 1, y + 1, z).setVisible(true);
			getTile(x + 1, y - 1, z).setVisible(true);

			getTile(x - 1, y + 1, z).setVisible(true);
			getTile(x - 1, y - 1, z).setVisible(true);
		}
	}

	private void setLadder(Tile tile) {
		int x = tile.getX();
		int y = tile.getY();
		int z = tile.getZ();

		if (tile.isLadderDown()) {

			TileLadderUp l = new TileLadderUp(x, y, z + 1);
			removeNode(world[x][y][z]);
			removeNode(world[x][y][z + 1]);
			world[x][y][z] = tile.id();
			world[x][y][z + 1] = l.id();
			addNode(tile);
			addNode(l);

			updateXYEdgesForTile(x, y, z);
			updateXYEdgesForTile(x - 1, y, z);
			updateXYEdgesForTile(x + 1, y, z);
			updateXYEdgesForTile(x, y - 1, z);
			updateXYEdgesForTile(x, y + 1, z);

			updateXYEdgesForTile(x, y, z + 1);
			updateXYEdgesForTile(x - 1, y, z + 1);
			updateXYEdgesForTile(x + 1, y, z + 1);
			updateXYEdgesForTile(x, y - 1, z + 1);
			updateXYEdgesForTile(x, y + 1, z + 1);

			addZEdgesForTile(x, y, z);
			addZEdgesForTile(x, y, z + 1);

			getTile(x + 1, y, z + 1).setVisible(true);
			getTile(x + -1, y, z + 1).setVisible(true);

			getTile(x, y + 1, z + 1).setVisible(true);
			getTile(x, y - 1, z + 1).setVisible(true);

			getTile(x + 1, y + 1, z + 1).setVisible(true);
			getTile(x + 1, y - 1, z + 1).setVisible(true);

			getTile(x - 1, y + 1, z + 1).setVisible(true);
			getTile(x - 1, y - 1, z + 1).setVisible(true);
		}

		if (tile.isLadderUp()) {

			TileLadderDown l = new TileLadderDown(x, y, z - 1);
			removeNode(world[x][y][z]);
			removeNode(world[x][y][z - 1]);
			world[x][y][z] = tile.id();
			world[x][y][z - 1] = l.id();
			addNode(tile);
			addNode(l);

			updateXYEdgesForTile(x, y, z);
			updateXYEdgesForTile(x - 1, y, z);
			updateXYEdgesForTile(x + 1, y, z);
			updateXYEdgesForTile(x, y - 1, z);
			updateXYEdgesForTile(x, y + 1, z);

			updateXYEdgesForTile(x, y, z - 1);
			updateXYEdgesForTile(x - 1, y, z - 1);
			updateXYEdgesForTile(x + 1, y, z - 1);
			updateXYEdgesForTile(x, y - 1, z - 1);
			updateXYEdgesForTile(x, y + 1, z - 1);

			addZEdgesForTile(x, y, z);
			addZEdgesForTile(x, y, z - 1);

			getTile(x + 1, y, z - 1).setVisible(true);
			getTile(x + -1, y, z - 1).setVisible(true);

			getTile(x, y + 1, z - 1).setVisible(true);
			getTile(x, y - 1, z - 1).setVisible(true);

			getTile(x + 1, y + 1, z - 1).setVisible(true);
			getTile(x + 1, y - 1, z - 1).setVisible(true);

			getTile(x - 1, y + 1, z - 1).setVisible(true);
			getTile(x - 1, y - 1, z - 1).setVisible(true);
		}
	}

	public void wasMined(Tile tileToMine) {
		if (tileToMine.getZ() == 0)
			setTile(new TileLand(tileToMine.getX(), tileToMine.getY(), tileToMine.getZ()));
		else {
			Tile t = new TileGround(tileToMine.getX(), tileToMine.getY(), tileToMine.getZ());
			t.setVisible(true);
			setTile(t);
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
					if (getTile(x, y, z).isLadderDown() || getTile(x, y, z).isLadderUp())
						addZEdgesForTile(x, y, z);
					if (getTile(x, y, z).isVisible()) {
						getTile(x + 1, y, z).setVisible(true);
						getTile(x + -1, y, z).setVisible(true);

						getTile(x, y + 1, z).setVisible(true);
						getTile(x, y - 1, z).setVisible(true);

						getTile(x + 1, y + 1, z).setVisible(true);
						getTile(x + 1, y - 1, z).setVisible(true);

						getTile(x - 1, y + 1, z).setVisible(true);
						getTile(x - 1, y - 1, z).setVisible(true);
					}
				}
			}
		}
	}

	public void setTileINITIAL(Tile tile) {
		if (tile.getX() < 0 || tile.getX() > Settings.WORLD_WIDTH - 1 || tile.getY() < 0
				|| tile.getY() > Settings.WORLD_HEIGHT - 1 || tile.getZ() < 0 || tile.getZ() > Settings.WORLD_DEPTH - 1)
			return;
		removeNode(world[tile.getX()][tile.getY()][tile.getZ()]);
		world[tile.getX()][tile.getY()][tile.getZ()] = tile.id();
		addNode(tile);
	}

}
