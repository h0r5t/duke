package core;

public class TileAir extends Tile {

	public TileAir(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundAir();
	}

}
