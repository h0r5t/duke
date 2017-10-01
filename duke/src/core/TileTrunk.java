package core;

public class TileTrunk extends Tile {

	public TileTrunk(int x, int y, int z) {
		super(GameData.getTileID("tile_trunk"), x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean canBeMined() {
		return true;
	}

	@Override
	public ItemDrop getDrop() {
		return new ItemDrop(new ItemWood(), 100);
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundAir();
	}
}
