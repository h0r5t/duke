package core;

public class TileTrunk extends Tile {

	public TileTrunk(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundWood();
	}

	@Override
	public Item getDrop() {
		return new ItemWood();
	}

}
