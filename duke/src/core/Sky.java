package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sky {

	private Color[][] colorMap;
	private BufferedImage skyImage;

	public Sky() {
		colorMap = new Color[Settings.WORLD_WIDTH][Settings.WORLD_HEIGHT];

		Color baseColor = Colors.COLOR_AIR;

		skyImage = new BufferedImage(Settings.WORLD_WIDTH * Settings.TILE_SIZE,
				Settings.WORLD_HEIGHT * Settings.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) skyImage.getGraphics();

		for (int x = 0; x < Settings.WORLD_WIDTH; x++) {
			for (int y = 0; y < Settings.WORLD_HEIGHT; y++) {
				colorMap[x][y] = ColorUtils.changeColor(baseColor, 5, -1);
				g.setColor(colorMap[x][y]);
				g.fillRect(x * Settings.TILE_SIZE, y * Settings.TILE_SIZE, Settings.TILE_SIZE, Settings.TILE_SIZE);
			}
		}

	}

	public BufferedImage getSkyImage() {
		return skyImage;
	}

	public Color getColor(int x, int y) {
		if (x < 0 || y < 0 || x >= Settings.WORLD_WIDTH || y >= Settings.WORLD_HEIGHT)
			return new Color(0, 0, 0);
		return colorMap[x][y];
	}

}
