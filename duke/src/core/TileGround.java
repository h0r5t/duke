package core;

public class TileGround extends Tile {

	public TileGround(int x, int y, int z) {
		super(GameData.getTileID("tile_ground"), x, y, z);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
