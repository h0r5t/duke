package core;

public class TileLadderUp extends Tile {

	public TileLadderUp(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
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
