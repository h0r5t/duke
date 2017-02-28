package core;

public class TileLadderDown extends Tile {
	public TileLadderDown(int x, int y, int z) {
		super(GameData.getTileID("tile_ladderdown"), x, y, z);
	}

	@Override
	public boolean tileCollides() {
		return false;
	}

	@Override
	public boolean isLadderDown() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}
}
