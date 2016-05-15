package core;

public class TileBush extends Tile {

	public TileBush(int x, int y, int z) {
		super(GameData.getTileID("tile_bush"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
