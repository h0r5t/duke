package core;

import java.awt.Color;

public class TileWoods extends Tile {

	public TileWoods() {
		generateTexture(Color.decode(Colors.COLOR_WOODS));
	}

	@Override
	public boolean collides() {
		return false;
	}

}
