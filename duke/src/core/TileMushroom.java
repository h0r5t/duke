package core;

public class TileMushroom extends Tile {

	public TileMushroom(int x, int y, int z) {
		super(Chars.getTileID("tile_mushroom"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
