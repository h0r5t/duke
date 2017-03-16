package core;

public class TileLeaves extends Tile {

	public TileLeaves(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundLeaves();
	}
}
