package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class Cursor implements Visual {

	private Core core;
	private int xpos;
	private int ypos;

	public Cursor(Core core, int x, int y) {
		this.core = core;
		xpos = x;
		ypos = y;
	}

	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void moveX(int x) {
		xpos += x;
		if (xpos < 0)
			xpos = 0;
		else if (xpos >= Settings.WORLD_WIDTH) {
			xpos = Settings.WORLD_WIDTH - 1;
		}
	}

	public void moveY(int y) {
		ypos += y;
		if (ypos < 0)
			ypos = 0;
		else if (ypos >= Settings.WORLD_HEIGHT) {
			ypos = Settings.WORLD_HEIGHT - 1;
		}
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Color.YELLOW);
		g.drawRect(posX, posY, Settings.TILE_SIZE - 1, Settings.TILE_SIZE - 1);
	}

	public void setToTile(Tile tile) {
		xpos = tile.getX();
		ypos = tile.getY();
	}

	public Tile getTile() {
		return Core.getWorld().getTile(xpos, ypos,
				core.getViewManager().getCurrentZ());
	}

}
