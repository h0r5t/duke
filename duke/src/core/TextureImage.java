package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureImage extends Texture {

	public TextureImage(BufferedImage source) {
		images = new BufferedImage[Settings.DRAW_DARKER_LEVELS_AMOUNT];
		int changeAlphaDelta = 300 / Settings.DRAW_DARKER_LEVELS_AMOUNT;

		for (int i = 0; i < images.length; i++) {
			BufferedImage b = new BufferedImage(Settings.TILE_SIZE, Settings.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics g = b.getGraphics();
			g.drawImage(source, 0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE, null);
			Color color = new Color(0, 0, 0, changeAlphaDelta * i);
			g.setColor(color);
			g.fillRect(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
			g.dispose();
			images[i] = b;
		}
	}

	@Override
	public void draw(Graphics2D g, int x, int y, int darkerLevel) {
		g.drawImage(images[darkerLevel], x, y, Settings.TILE_SIZE, Settings.TILE_SIZE, null);
	}

}
