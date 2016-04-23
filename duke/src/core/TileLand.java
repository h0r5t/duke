package core;

import java.awt.Color;

public class TileLand extends Tile {

	public TileLand() {
		generateTexture(Color.decode(Colors.COLOR_LAND));
	}

	@Override
	public boolean collides() {
		return false;
	}

}
