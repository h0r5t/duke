package core;

public class TileLand extends Tile {

	public TileLand(int x, int y, int z) {
		super(Chars.getTileID("tile_land"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
