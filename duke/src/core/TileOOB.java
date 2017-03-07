package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class TileOOB extends Tile {

	public TileOOB(int x, int y, int z) {
		super(-1, x, y, z);
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		g.setColor(Color.BLACK);
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
	}

	@Override
	public boolean tileCollides() {
		return true;
	}

}
