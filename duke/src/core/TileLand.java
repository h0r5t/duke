package core;

public class TileLand extends Tile {

	public TileLand(int x, int y) {
		super(Chars.getTileID("tile_land"), x, y);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
