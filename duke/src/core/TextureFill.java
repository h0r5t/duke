package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureFill extends Texture {

	public TextureFill(Color color) {

		BufferedImage img = new BufferedImage(Settings.TILE_SIZE, Settings.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
		g.dispose();
		image = img;
		enhance();
	}

	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(image, x, y, Settings.TILE_SIZE, Settings.TILE_SIZE, null);
	}

}
