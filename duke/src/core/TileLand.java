package core;

public class TileLand extends Tile {

	public TileLand(int x, int y, int z) {
		super(GameData.getTileID("tile_land"), x, y, z);
	}

	@Override
	public boolean tileCollides() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}
