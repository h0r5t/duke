package core;

public class TileTrunkOnGround extends Tile {

	public TileTrunkOnGround(int x, int y, int z) {
		super(GameData.getTileID("tile_trunk_on_ground"), x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public ItemDrop getDrop() {
		return new ItemDrop(new ItemWood(), 100);
	}

	@Override
	public boolean canBeMined() {
		return true;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundGrass();
	}

}
