package core;

public class TileLand extends Tile {

	public TileLand(int x, int y, int z) {
		super(GameData.getTileID("tile_land"), x, y, z);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundGrass();
	}

}
