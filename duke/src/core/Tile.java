package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import pathfinder.GraphNode;

public abstract class Tile extends GraphNode implements Visual {

	protected Character myChar;
	protected int tileID;

	public Tile(int tileID, int x, int y) {
		super(UniqueIDFactory.getID(), x, y);
		this.tileID = tileID;
		getChar();
	}

	protected void getChar() {
		if (tileID == -1)
			return;
		myChar = Chars.getRandomCharacter(getTileID());
	}

	public int getTileID() {
		return tileID;
	}

	public abstract boolean collides();

	public int getX() {
		return (int) super.x();
	}

	public int getY() {
		return (int) super.y();
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Color.BLACK);
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);

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

		if (!Settings.DRAW_TILE_BORDERS)
			return;
		g.setColor(Color.DARK_GRAY);
		g.drawRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
	}

}
