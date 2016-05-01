package core;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Unit implements Visual {

	protected int x;
	protected int y;
	protected int unitID;
	protected Character myChar;

	public Unit(int id, int x, int y) {
		this.unitID = id;
		this.x = x;
		this.y = y;
		getChar();
	}

	protected void getChar() {
		myChar = Chars.getRandomUnitCharacter(unitID);
	}

	public int getUnitID() {
		return unitID;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void moveTo(int xpos, int ypos) {
		Core.getWorld().moveUnit(this, x, y, xpos, ypos);
		this.x = xpos;
		this.y = ypos;
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		Font font = new Font("Arial", Font.BOLD, Settings.CHAR_FONT_SIZE);
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
