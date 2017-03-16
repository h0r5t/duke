package core;

public class TileGround extends Tile {

	public TileGround(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
