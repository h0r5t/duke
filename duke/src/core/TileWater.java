package core;

import java.awt.Color;

public class TileWater extends Tile {

	public TileWater(int x, int y) {
		super(x, y);
		generateTexture(Color.decode(Colors.COLOR_WATER));
	}

	@Override
	public boolean collides() {
		return true;
	}

}
