package core;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Item implements Visual {

	private int itemID;
	private Character myChar;

	public Item(int itemID) {
		this.itemID = itemID;
		getChar();
	}

	protected void getChar() {
		myChar = Chars.getRandomItemCharacter(getItemID());
	}

	public int getItemID() {
		return itemID;
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Colors.COLOR_BG);
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);

		Font font = new Font("Arial", Font.BOLD, myChar.getFontSize());
		g.setColor(myChar.getColor());

		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE,
				Settings.TILE_SIZE);
		String text = myChar.getChar() + "";
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, posX + x, posY + y);
	}

}
