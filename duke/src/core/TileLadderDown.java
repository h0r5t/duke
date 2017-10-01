package core;

public class TileLadderDown extends Tile {
	public TileLadderDown(int x, int y, int z) {
		super(GameData.getTileID("tile_ladder_down"), x, y, z);
	}

	@Override
	public boolean isSolid() {
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
