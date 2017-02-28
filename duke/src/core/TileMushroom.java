package core;

public class TileMushroom extends Tile {

	public TileMushroom(int x, int y, int z) {
		super(GameData.getTileID("tile_mushroom"), x, y, z);
	}

	@Override
	public boolean tileCollides() {
		return false;
	}

}
