package core;

public class TileLadderDown extends Tile {
	public TileLadderDown(int x, int y, int z) {
		super(Chars.getTileID("tile_ladderdown"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

	@Override
	public boolean isLadderDown() {
		return true;
	}
}
