package core;

public class TileWater extends Tile {

	public TileWater(int x, int y) {
		super(Chars.getTileID("tile_water"), x, y);
	}

	@Override
	public boolean collides() {
		return true;
	}

}
