package core;

import java.awt.Color;

public class TileLand extends Tile {

	public TileLand(int x, int y) {
		super(x, y);
		generateTexture(Color.decode(Colors.COLOR_LAND));
	}

	@Override
	public boolean collides() {
		return false;
	}

}
