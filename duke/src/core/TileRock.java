package core;

public class TileRock extends Tile {

	public TileRock(int x, int y, int z) {
		super(Chars.getTileID("tile_rock"), x, y, z);
	}

	@Override
	public boolean collides() {
		return true;
	}

}
