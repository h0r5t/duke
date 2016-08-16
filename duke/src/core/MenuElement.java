package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MenuElement implements Visual {

	private String id;
	private OnClickListener listener;

	public MenuElement(String id, OnClickListener listener) {
		this.id = id;
		this.listener = listener;
	}

	public void wasClicked() {
		listener.eventFired(id);
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(posX, posY, Settings.MENU_GROUP_WIDTH,
				Settings.MENU_ELEMENT_HEIGHT);

		g.setColor(Color.BLACK);
		g.drawRect(posX, posY, Settings.MENU_GROUP_WIDTH,
				Settings.MENU_ELEMENT_HEIGHT);

		Font font = new Font("Arial", Font.PLAIN, 15);
		g.setColor(Color.LIGHT_GRAY);
		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.MENU_GROUP_WIDTH,
				Settings.MENU_HEIGHT);
		int x = (rect.width - metrics.stringWidth(id)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2);
		g.setFont(font);
		g.drawString(id, posX + x, posY + y - 5);
	}

}
