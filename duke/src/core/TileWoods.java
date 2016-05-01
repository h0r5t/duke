package core;

public class TileWoods extends Tile {

	public TileWoods(int x, int y) {
		super(Chars.getTileID("tile_woods"), x, y);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
