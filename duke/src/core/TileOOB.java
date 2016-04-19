package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class TileOOB extends Tile {

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Color.BLACK);
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
	}

}
