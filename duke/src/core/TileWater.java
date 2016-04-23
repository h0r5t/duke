package core;

import java.awt.Color;

public class TileWater extends Tile {

	public TileWater() {
		generateTexture(Color.decode(Colors.COLOR_WATER));
	}

	@Override
	public boolean collides() {
		return true;
	}

}
