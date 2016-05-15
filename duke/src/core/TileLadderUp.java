package core;

public class TileLadderUp extends Tile {

	public TileLadderUp(int x, int y, int z) {
		super(GameData.getTileID("tile_ladderup"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

	@Override
	public boolean isLadderUp() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}
