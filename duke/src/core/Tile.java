package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Tile implements Visual {

	protected BufferedImage texture;

	protected void generateTexture(Color baseColor) {
		ColorAttributes myData = new ColorAttributes(baseColor,
				Settings.TEXTURE_COLOR_VARIATION,
				Settings.TEXTURE_ALPHA_VARIATION);
		texture = TextureGenerator.generateRectangle(myData,
				Settings.TEXTURE_SECTION_SIZE, false, Settings.TILE_SIZE,
				Settings.TILE_SIZE);
	}

	public abstract boolean collides();

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
