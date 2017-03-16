package core;

public class TileShrub extends Tile {

	public TileShrub(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundGrass();
	}

}
