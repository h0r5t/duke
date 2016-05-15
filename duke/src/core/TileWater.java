package core;

public class TileWater extends Tile {

	public TileWater(int x, int y, int z) {
		super(GameData.getTileID("tile_water"), x, y, z);
	}

	@Override
	public boolean collides() {
		return true;
	}

}
