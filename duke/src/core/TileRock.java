package core;

public class TileRock extends Tile {

	public TileRock(int x, int y, int z) {
		super(GameData.getTileID("tile_rock"), x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean needsRamp() {
		return true;
	}

	@Override
	public boolean canBeMined() {
		return true;
	}

	@Override
	public ItemDrop getDrop() {
		return new ItemDrop(new ItemStone(), 40);
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundRock();
	}

}
