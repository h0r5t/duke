package core;

public class TileWoods extends Tile {

	public TileWoods(int x, int y, int z) {
		super(Chars.getTileID("tile_woods"), x, y, z);
	}

	@Override
	public boolean collides() {
		return true;
	}

}
