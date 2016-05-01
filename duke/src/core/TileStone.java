package core;

public class TileStone extends Tile {

	public TileStone(int x, int y, int z) {
		super(Chars.getTileID("tile_stone"), x, y, z);
	}

	@Override
	public boolean collides() {
		return true;
	}

}
