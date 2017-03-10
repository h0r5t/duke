package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Projectile {

	protected Tile tilePosition;
	protected double microPositionX;
	protected double microPositionY;
	private Character myChar;
	protected World world;

	public Projectile(World world) {
		this.world = world;
		myChar = new Character("+", Color.RED, 12);
		microPositionX = Settings.TILE_SIZE / 2;
		microPositionY = Settings.TILE_SIZE / 2;
	}

	protected void attachToTile(Tile newTile) {
		if (tilePosition != null)
			tilePosition.removeProjectile(this);
		newTile.addProjectile(this);
		tilePosition = newTile;
	}

	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		posX += (int) (microPositionX + 0.5);
		posY += (int) (microPositionY + 0.5);

		Font font = new Font("Arial", Font.BOLD, myChar.getFontSize());
		g.setColor(myChar.getColor());

		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
		String text = myChar.getChar() + "";
		int width = metrics.stringWidth(text);
		int height = metrics.getHeight();
		g.setFont(font);
		g.drawString(text, posX - width, posY + metrics.getHeight() / 2);
	}

	public abstract void update();

}
