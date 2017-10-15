package core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LayerBasedRenderer {

	private BufferedImage[] layers;
	private Graphics2D[] graphics;

	public LayerBasedRenderer() {
		// 0 - gets drawn first
		// 4 - gets drawn last
		layers = new BufferedImage[5];
		graphics = new Graphics2D[layers.length];
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new BufferedImage(Settings.GAME_FRAME_WIDTH + 50, Settings.GAME_FRAME_HEIGHT + 50,
					BufferedImage.TYPE_INT_ARGB);
			graphics[i] = (Graphics2D) layers[i].getGraphics();
			graphics[i].setBackground(Colors.COLOR_TRANSPARENT);
			graphics[i].clearRect(0, 0, layers[i].getWidth(), layers[i].getHeight());
		}
	}

	public void renderInLayer(int layer, Drawable d, int renderX, int renderY) {
		d.draw(graphics[layer], renderX, renderY);
	}

	public BufferedImage getLayer(int layer) {
		graphics[layer].dispose();
		return layers[layer];
	}

	public BufferedImage[] getLayers() {
		return layers;
	}

}
