package core;

public class TileShrub extends Tile {

	public TileShrub(int x, int y, int z) {
		super(Chars.getTileID("tile_shrub"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
