package core;

public class TileGrass extends Tile {

	public TileGrass(int x, int y, int z) {
		super(GameData.getTileID("tile_grass"), x, y, z);
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
