package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class TextureCharacter extends Texture {

	private Character myChar;

	public TextureCharacter(Character c) {
		this.myChar = c;
		Font font = new Font("Arial", Font.BOLD, c.getFontSize());
		Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);

		images = new BufferedImage[Settings.DRAW_DARKER_LEVELS_AMOUNT];
		for (int i = 0; i < images.length; i++) {
			BufferedImage img = new BufferedImage(Settings.TILE_SIZE, Settings.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) img.getGraphics();
			Color fgColor = ColorUtils.makeColorDarker(c.getColor(), i, c.getColor().getAlpha());

			if (c.isFill()) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
				g.setColor(fgColor);
				g.fillRect(1, 1, Settings.TILE_SIZE - 2, Settings.TILE_SIZE - 2);

			} else {
				if (c.getBgColor() != null) {
					Color bgColor = ColorUtils.makeColorDarker(c.getBgColor(), i, c.getBgColor().getAlpha());
					g.setColor(bgColor);
					g.fillRect(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
				}
				g.setColor(fgColor);
				FontMetrics metrics = g.getFontMetrics(font);
				String text = c.getChar() + "";
				int x = (rect.width - metrics.stringWidth(text)) / 2;
				int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
				g.setFont(font);
				g.drawString(text, x, y);
			}

			images[i] = img;
		}
	}

	public void draw(Graphics2D g, int x, int y, int darkerLevel) {
		g.drawImage(images[darkerLevel], x, y, Settings.TILE_SIZE, Settings.TILE_SIZE, null);
	}

	public Character getCharacter() {
		return myChar;
	}

}
