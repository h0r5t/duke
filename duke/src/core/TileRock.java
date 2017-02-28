package core;

public class TileRock extends Tile {

	public TileRock(int x, int y, int z) {
		super(GameData.getTileID("tile_rock"), x, y, z);
	}

	@Override
	public boolean tileCollides() {
		return true;
	}

	@Override
	public boolean canBeMined() {
		return true;
	}

	@Override
	public Item getDrop() {
		return new ItemStone();
	}

}
