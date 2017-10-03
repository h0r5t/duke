package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureImage extends Texture {

	public TextureImage(BufferedImage source) {
		BufferedImage b = new BufferedImage(Settings.TILE_SIZE, Settings.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = b.getGraphics();
		int startx = (Settings.TILE_SIZE - source.getWidth()) / 2;
		int starty = (Settings.TILE_SIZE - source.getHeight()) / 2;
		g.drawImage(source, startx, starty, null);
		g.dispose();
		image = b;
		enhance();
	}

	@Override
	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(image, x, y, Settings.TILE_SIZE, Settings.TILE_SIZE, null);
	}

}
