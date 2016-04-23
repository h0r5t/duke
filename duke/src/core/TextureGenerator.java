package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TextureGenerator {

	public static BufferedImage generateRectangle(ColorAttributes myData,
			int sectionSize, boolean blackBorder, int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		for (int x = 0; x < image.getWidth(); x += sectionSize) {
			for (int y = 0; y < image.getHeight(); y += sectionSize) {
				int r = myData.getRandomRed();
				int g = myData.getRandomGreen();
				int b = myData.getRandomBlue();
				int a = myData.getRandomAlpha();
				int col = (a << 24) | (r << 16) | (g << 8) | b;
				graphics.setColor(new Color(col));
				graphics.fillRect(x, y, sectionSize, sectionSize);
			}
		}

		if (blackBorder) {
			graphics.setColor(Color.BLACK);
			graphics.drawRect(0, 0, image.getWidth() - 1,
					image.getHeight() - 1);
		}

		return image;
	}

}
