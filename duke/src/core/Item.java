package core;

import java.awt.Graphics2D;

public abstract class Item implements Visual {

	public Item() {

	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		// TODO
		//
		// Font font = new Font("Arial", Font.BOLD, myChar.getFontSize());
		// g.setColor(myChar.getColor());
		//
		// FontMetrics metrics = g.getFontMetrics(font);
		// Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE,
		// Settings.TILE_SIZE);
		// String text = myChar.getChar() + "";
		// int x = (rect.width - metrics.stringWidth(text)) / 2;
		// int y = ((rect.height - metrics.getHeight()) / 2) +
		// metrics.getAscent();
		// g.setFont(font);
		// g.drawString(text, posX + x, posY + y);
		//
		// if (!Settings.DRAW_TILE_BORDERS)
		// return;
		// g.setColor(Color.DARK_GRAY);
		// g.drawRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
	}

}
