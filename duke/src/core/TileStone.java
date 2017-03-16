package core;

public class TileStone extends Tile {

	public TileStone(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
