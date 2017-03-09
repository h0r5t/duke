package core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Texture {

	protected BufferedImage[] images;

	public abstract void draw(Graphics2D g, int x, int y, int darkerLevel);

	public BufferedImage getImage(int darkerLevel) {
		return images[darkerLevel];
	}
}
