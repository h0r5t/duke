package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import pathfinder.GraphNode;

public abstract class Tile extends GraphNode implements Visual {

	protected Character myChar;
	protected int tileID;
	private boolean isVisible;
	private ArrayList<SelectionArea> selections;

	public Tile(int tileID, int x, int y, int z) {
		super(UniqueIDFactory.getID(), x, y, z);
		this.tileID = tileID;
		this.selections = new ArrayList<SelectionArea>();
		getChar();
		if (z == 0) {
			setVisible(true);
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isSelected() {
		return selections.size() > 0;
	}

	public void select(SelectionArea selection) {
		if (selections.contains(selection))
			return;
		selections.add(selection);
	}

	public void deselect(SelectionArea selection) {
		selections.remove(selection);
	}

	protected void getChar() {
		if (tileID == -1)
			return;
		myChar = Chars.getRandomTileCharacter(getTileID());
	}

	public int getTileID() {
		return tileID;
	}

	public abstract boolean collides();

	public boolean isLadderDown() {
		return false;
	}

	public boolean isLadderUp() {
		return false;
	}

	public int getX() {
		return (int) super.x();
	}

	public int getY() {
		return (int) super.y();
	}

	public int getZ() {
		return (int) super.z();
	}

	public Coords3D getCoords3D() {
		return new Coords3D(getX(), getY(), getZ());
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Colors.COLOR_BG);
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);

		if (isVisible()) {
			Font font = new Font("Arial", Font.BOLD, myChar.getFontSize());
			g.setColor(myChar.getColor());

			FontMetrics metrics = g.getFontMetrics(font);
			Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE,
					Settings.TILE_SIZE);
			String text = myChar.getChar() + "";
			int x = (rect.width - metrics.stringWidth(text)) / 2;
			int y = ((rect.height - metrics.getHeight()) / 2)
					+ metrics.getAscent();
			g.setFont(font);
			g.drawString(text, posX + x, posY + y);
		}

		if (Settings.DRAW_TILE_BORDERS) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
		}

		if (isSelected()) {
			g.setColor(Color.CYAN);
			g.drawRect(posX + 1, posY + 1, Settings.TILE_SIZE - 3,
					Settings.TILE_SIZE - 3);
		}

	}

}
