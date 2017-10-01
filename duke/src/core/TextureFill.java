package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureFill extends Texture {

	public TextureFill(Color color) {
		images = new BufferedImage[Settings.DRAW_DARKER_LEVELS_AMOUNT];

		for (int i = 0; i < images.length; i++) {
			BufferedImage img = new BufferedImage(Settings.TILE_SIZE, Settings.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) img.getGraphics();
			g.setColor(ColorUtils.makeColorDarker(color, i, color.getAlpha()));
			g.fillRect(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);

			images[i] = img;
		}
	}

	public void draw(Graphics2D g, int x, int y, int darkerLevel) {
		g.drawImage(images[darkerLevel], x, y, Settings.TILE_SIZE, Settings.TILE_SIZE, null);
	}

}
