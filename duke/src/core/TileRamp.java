package core;

public class TileRamp extends Tile {

	private Direction direction;

	public TileRamp(int x, int y, int z, Direction d) {
		super(GameData.getTileID("tile_ramp"), x, y, z);
		this.direction = d;
	}

	public Direction getDirection() {
		return direction;
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
	public boolean isLadderUp() {
		return true;
	}

}
