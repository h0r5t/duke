package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureBorder {

	private BufferedImage[] images;

	public TextureBorder(float stroke, Direction location) {
		images = new BufferedImage[1];
		BufferedImage img = new BufferedImage(Settings.TILE_SIZE + 4, Settings.TILE_SIZE + 4,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(stroke));

		int t = img.getWidth();
		if (location == Direction.LEFT) {
			g.drawLine(2, 2, 2, t - 2);
		} else if (location == Direction.RIGHT) {
			g.drawLine(t - 2, 2, t - 2, t - 2);
		} else if (location == Direction.TOP) {
			g.drawLine(2, 2, t + 2, 2);
		} else if (location == Direction.BOTTOM) {
			g.drawLine(2, t - 2, t - 2, t - 2);
		}

		images[0] = img;
	}

	public void draw(Graphics2D g, int x, int y, int darkerLevel) {
		g.drawImage(images[0], x - 2, y - 2, images[0].getWidth(), images[0].getHeight(), null);
	}

}
