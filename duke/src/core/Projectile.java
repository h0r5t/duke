package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Projectile implements Visual {

	protected Tile tilePosition;
	protected double microPositionX;
	protected double microPositionY;
	private Character myChar;
	protected World world;

	public Projectile(World world) {
		this.world = world;
		myChar = new Character("+", Color.RED, 12);
		microPositionX = 0;
		microPositionY = 0;
	}

	protected void attachToTile(Tile newTile) {
		if (tilePosition != null)
			tilePosition.removeProjectile(this);
		newTile.addProjectile(this);
		tilePosition = newTile;
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		posX += microPositionX;
		posY += microPositionY;

		Font font = new Font("Arial", Font.BOLD, myChar.getFontSize());
		g.setColor(myChar.getColor());

		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
		String text = myChar.getChar() + "";
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, posX + x, posY + y);
	}

	public abstract void update();

}
