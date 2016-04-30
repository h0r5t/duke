package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pathfinder.GraphNode;

public abstract class Tile extends GraphNode implements Visual {

	protected BufferedImage texture;

	public Tile(int x, int y) {
		super(UniqueIDFactory.getID(), x, y);
	}

	protected void generateTexture(Color baseColor) {
		ColorAttributes myData = new ColorAttributes(baseColor,
				Settings.TEXTURE_COLOR_VARIATION,
				Settings.TEXTURE_ALPHA_VARIATION);
		texture = TextureGenerator.generateRectangle(myData,
				Settings.TEXTURE_SECTION_SIZE, false, Settings.TILE_SIZE,
				Settings.TILE_SIZE);
	}

	public abstract boolean collides();

	public int getX() {
		return (int) super.x();
	}

	public int getY() {
		return (int) super.y();
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.drawImage(texture, posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE,
				null);

		if (!Settings.DRAW_TILE_BORDERS)
			return;
		g.setColor(Color.DARK_GRAY);
		g.drawRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
	}

}
