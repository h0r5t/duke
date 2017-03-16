package core;

public class TileTree extends Tile {

	public TileTree(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Item getDrop() {
		return new ItemWood();
	}

	@Override
	public boolean canBeMined() {
		return true;
	}

	@Override
	public Ground getDefaultGround() {
		return new GroundGrass();
	}

}
