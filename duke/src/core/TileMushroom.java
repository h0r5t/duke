package core;

public class TileMushroom extends Tile {

	public TileMushroom(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
