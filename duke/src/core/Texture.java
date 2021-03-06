package core;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public abstract class Texture {

	protected BufferedImage image;

	public static BufferedImage makeCompatible(BufferedImage image) {
		return toCompatibleImage(image);
	}

	private static BufferedImage toCompatibleImage(BufferedImage image) {
		// obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(image.getWidth(), image.getHeight(),
				image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image;
	}

	public abstract void draw(Graphics2D g, int x, int y);
}
