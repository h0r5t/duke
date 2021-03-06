package core;

import java.util.ArrayList;
import java.util.Random;

import pathfinder.Graph;

public class World extends Graph {

	private Core core;
	private boolean createEdgesWhenPlacingTiles;
	private int[][][] tiles;
	private ItemManager itemManager;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Projectile> projectilesToRemove;

	public World(Core core) {
		super();
		this.core = core;
		createEdgesWhenPlacingTiles = false;
		tiles = new int[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT][Settings.WORLD_DEPTH];
		itemManager = new ItemManager(core);
		projectiles = new ArrayList<>();
		projectilesToRemove = new ArrayList<>();
	}

	public void update(Core core) {
		itemManager.update(core);
		for (Projectile p : projectiles) {
			if (p != null)
				p.update();
		}
		projectiles.removeAll(projectilesToRemove);
	}

	public ArrayList<Unit> getUnitsAt(Coords3D coords) {
		return core.getUnitManager().getUnitsAt(coords);
	}

	public void removeUnit(Unit unit) {
		core.getUnitManager().removeUnit(unit);
	}

	public void addProjectileAt(Projectile p, Coords3D c) {
		p.attachToTile(c.getTile());
		projectiles.add(p);
	}

	public void removeProjectile(Projectile p) {
		projectilesToRemove.add(p);
	}

	public void addItem(Item item, Coords3D c) {
		itemManager.addItem(item, c);
	}

	public void removeItem(Item item) {
		itemManager.removeItem(item);
	}

	public ArrayList<Item> getItemsAt(Coords3D c) {
		return itemManager.getItemsAt(c);
	}

	public Coords3D getItemPos(Item item) {
		return itemManager.getItemPos(item);
	}

	public ArrayList<Item> getItems() {
		return itemManager.getItems();
	}

	public void addFluid(Fluid f, Coords3D coords) {
		f.setCoords(coords);
		if (!f.getCoords().getTile().hasFluid())
			core.getFluidManager().addFluid(f);
		f.activate();
	}

	public void setTile(Tile tile) {
		if (!createEdgesWhenPlacingTiles) {
			setTileINITIAL(tile);
			return;
		}

		Tile previousTile = getTile(tile.getCoords3D());
		if (!tile.overridesOldGround())
			tile.setGround(previousTile.getGround());
		tile.setExposed(previousTile.isExposed());

		// check for near fluids
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					Tile t = getTile(tile.getX() + x, tile.getY() + y, tile.getZ() + z);
					if (t.hasFluid())
						t.getFluid().setStatic(false);
				}
			}
		}

		// check if building
		if (previousTile instanceof Building)
			core.getLogisticsManager().getBuildingManager().removeBuilding((Building) previousTile);
		if (tile instanceof Building)
			core.getLogisticsManager().getBuildingManager().addBuilding((Building) tile);

		int x = tile.getX();
		int y = tile.getY();
		int z = tile.getZ();

		removeNode(tiles[x][y][z]);
		tiles[x][y][z] = tile.id();
		addNode(tile);

		updateXYEdgesForTile(x, y, z);
		updateXYEdgesForTile(x - 1, y, z);
		updateXYEdgesForTile(x + 1, y, z);
		updateXYEdgesForTile(x, y - 1, z);
		updateXYEdgesForTile(x, y + 1, z);

		// check if ladder
		if (tile instanceof TileRamp) {
			addZEdgesForTile(x, y, z);
		}

		if (getTile(x, y, z).isExposed()) {
			getTile(x + 1, y, z).setExposed(true);
			getTile(x + -1, y, z).setExposed(true);

			getTile(x, y + 1, z).setExposed(true);
			getTile(x, y - 1, z).setExposed(true);

			getTile(x + 1, y + 1, z).setExposed(true);
			getTile(x + 1, y - 1, z).setExposed(true);

			getTile(x - 1, y + 1, z).setExposed(true);
			getTile(x - 1, y - 1, z).setExposed(true);
		}

		Core.getEventHub().fireEvent(new EventTileChanged(previousTile, tile));
	}

	public void wasMined(Coords3D targetToMine) {
		Item droppedItem = targetToMine.getTile().getDrop().getItem();

		Tile t = new TileGround(targetToMine.getX(), targetToMine.getY(), targetToMine.getZ());
		setTile(t);
		t.setExposed(true);

		if (droppedItem != null) {
			addItem(droppedItem, getRandomSurroundingLocation(targetToMine));
			itemManager.claimItem(droppedItem);
		}

		core.getTaskDistributor().wasMined(targetToMine);
	}

	private Coords3D getRandomSurroundingLocation(Coords3D target) {
		Random random = new Random();
		int r = random.nextInt(2);
		if (r == 0)
			return target;

		ArrayList<Coords3D> list = getReachableSurroundingTiles(target);
		r = new Random().nextInt(list.size());
		return list.get(r);
	}

	public ArrayList<Coords3D> getReachableSurroundingTiles(Coords3D target) {
		int x = target.getX();
		int y = target.getY();

		ArrayList<Coords3D> possibleTargets = new ArrayList<Coords3D>();
		Tile t1 = Core.getWorld().getTile(x + 1, y, target.getZ());
		Tile t2 = Core.getWorld().getTile(x - 1, y, target.getZ());
		Tile t3 = Core.getWorld().getTile(x, y + 1, target.getZ());
		Tile t4 = Core.getWorld().getTile(x, y - 1, target.getZ());

		if (!t1.blocksPath())
			possibleTargets.add(t1.getCoords3D());
		if (!t2.blocksPath())
			possibleTargets.add(t2.getCoords3D());
		if (!t3.blocksPath())
			possibleTargets.add(t3.getCoords3D());
		if (!t4.blocksPath())
			possibleTargets.add(t4.getCoords3D());

		return possibleTargets;
	}

	public Tile getTile(int x, int y, int z) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight() || z < 0 || z >= getDepth())
			return new TileOOB(x, y, z);
		Tile t = (Tile) getNode(tiles[x][y][z]);
		if (t == null)
			return new TileOOB(x, y, z);
		return (Tile) getNode(tiles[x][y][z]);
	}

	public Tile getTile(Coords3D coords) {
		return getTile(coords.getX(), coords.getY(), coords.getZ());
	}

	public int getWidth() {
		return tiles.length;
	}

	public int getHeight() {
		return tiles[0].length;
	}

	public int getDepth() {
		return tiles[0][0].length;
	}

	public void updateXYEdgesForTile(int x, int y, int z) {
		if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length)
			return;

		if (getTile(x, y, z).blocksPath() || getTile(x, y, z).isAir()) {
			return;
		}

		if (x > 0) {
			if (!getTile(x - 1, y, z).blocksPath() && !getTile(x - 1, y, z).isAir()) {
				addEdge(tiles[x][y][z], tiles[x - 1][y][z], 0);
			} else {
				try {
					removeEdge(tiles[x][y][z], tiles[x - 1][y][z]);
				} catch (Exception e) {
				}
			}
		}
		if (x < Settings.WORLD_WIDTH - 1) {
			if (!getTile(x + 1, y, z).blocksPath() && !getTile(x + 1, y, z).isAir()) {
				addEdge(tiles[x][y][z], tiles[x + 1][y][z], 0);
			} else {
				try {
					removeEdge(tiles[x][y][z], tiles[x + 1][y][z]);
				} catch (Exception e) {
				}
			}
		}
		if (y > 0) {
			if (!getTile(x, y - 1, z).blocksPath() && !getTile(x, y - 1, z).isAir()) {
				addEdge(tiles[x][y][z], tiles[x][y - 1][z], 0);
			} else {
				try {
					removeEdge(tiles[x][y][z], tiles[x][y - 1][z]);
				} catch (Exception e) {
				}
			}
		}
		if (y < Settings.WORLD_HEIGHT - 1) {
			if (!getTile(x, y + 1, z).blocksPath() && !getTile(x, y + 1, z).isAir()) {
				addEdge(tiles[x][y][z], tiles[x][y + 1][z], 0);
			} else {
				try {
					removeEdge(tiles[x][y][z], tiles[x][y + 1][z]);
				} catch (Exception e) {
				}
			}
		}
	}

	public void addZEdgesForTile(int x, int y, int z) {
		if (z > 0) {
			TileRamp tile = (TileRamp) getTile(x, y, z);
			Direction d = tile.getDirection();
			if (d == Direction.LEFT && x > 0) {
				addEdge(tiles[x][y][z], tiles[x - 1][y][z - 1], 0);
				addEdge(tiles[x - 1][y][z - 1], tiles[x][y][z], 0);
			} else if (d == Direction.RIGHT && x < Settings.WORLD_WIDTH) {
				addEdge(tiles[x][y][z], tiles[x + 1][y][z - 1], 0);
				addEdge(tiles[x + 1][y][z - 1], tiles[x][y][z], 0);
			} else if (d == Direction.BOTTOM && y < Settings.WORLD_HEIGHT) {
				addEdge(tiles[x][y][z], tiles[x][y + 1][z - 1], 0);
				addEdge(tiles[x][y + 1][z - 1], tiles[x][y][z], 0);
			} else if (d == Direction.TOP && y > 0) {
				addEdge(tiles[x][y][z], tiles[x][y - 1][z - 1], 0);
				addEdge(tiles[x][y - 1][z - 1], tiles[x][y][z], 0);
			}

		}

		// if (tile.isLadderDown()) {
		// if (z < Settings.WORLD_DEPTH - 1) {
		// addEdge(tiles[x][y][z], tiles[x][y][z + 1], 0);
		// }
		// }
		//
		// if (tile.isLadderUp()) {
		// if (z > 0) {
		// addEdge(tiles[x][y][z], tiles[x][y][z - 1], 0);
		// }
		// }
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void createInitialEdges() {
		createEdgesWhenPlacingTiles = true;
		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					updateXYEdgesForTile(x, y, z);
				}
			}
		}

		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				for (int z = 0; z < Settings.WORLD_DEPTH; z++) {
					if (getTile(x, y, z).isLadderDown() || getTile(x, y, z).isLadderUp())
						addZEdgesForTile(x, y, z);
				}
			}
		}
	}

	private void setTileINITIAL(Tile tile) {
		if (tile.getX() < 0 || tile.getX() > Settings.WORLD_WIDTH - 1 || tile.getY() < 0
				|| tile.getY() > Settings.WORLD_HEIGHT - 1 || tile.getZ() < 0 || tile.getZ() > Settings.WORLD_DEPTH - 1)
			return;
		Tile previousTile = getTile(tile.getCoords3D());
		if (previousTile != null && previousTile.getGround() != null && !tile.overridesOldGround())
			tile.setGround(previousTile.getGround());
		removeNode(tiles[tile.getX()][tile.getY()][tile.getZ()]);
		tiles[tile.getX()][tile.getY()][tile.getZ()] = tile.id();
		addNode(tile);
	}

}
