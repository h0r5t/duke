package core;

public class TileTree extends Tile {

	public TileTree(int x, int y, int z) {
		super(GameData.getTileID("tile_woods"), x, y, z);
	}

	@Override
	public boolean collides() {
		return true;
	}

	@Override
	public Item getDrop() {
		return new ItemWood();
	}

	@Override
	public boolean canBeMined() {
		return true;
	}

}
