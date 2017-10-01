package core;

public class TileLeaves extends Tile {

	public TileLeaves(int x, int y, int z) {
		super(GameData.getTileID("tile_leaves"), x, y, z);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
