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
	protected Ground ground;
	private boolean isVisible;
	private ArrayList<Zone2D> selections;
	private ArrayList<Projectile> projectiles;

	public Tile(int tileID, int x, int y, int z) {
		super(UniqueIDFactory.getID(), x, y, z);
		this.tileID = tileID;
		resetGround();
		this.selections = new ArrayList<Zone2D>();
		this.projectiles = new ArrayList<Projectile>();
		getChar();
		if (z == 0) {
			setVisible(true);
		}
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public Item getDrop() {
		return null;
	}

	public boolean canBeMined() {
		return false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isSelected() {
		if (selections.size() > 0)
			return true;
		return false;
	}

	public void select(Zone2D selection) {
		if (selections.contains(selection))
			return;
		selections.add(selection);
	}

	public void deselect(Zone2D selection) {
		selections.remove(selection);
	}

	protected void getChar() {
		if (tileID == -1)
			return;
		myChar = GameData.getRandomTileCharacter(getTileID());
	}

	public int getTileID() {
		return tileID;
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	public void removeProjectile(Projectile p) {
		projectiles.remove(p);
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public boolean collides() {
		if (tileCollides())
			return true;
		if (Core.getWorld() == null)
			return false;
		// if (Core.getWorld().getUnitsAt(this) != null)
		// return true;
		for (Item i : Core.getWorld().getItemsAt(this.getCoords3D())) {
			if (i.collides())
				return true;
		}
		return false;
	}

	public abstract boolean tileCollides();

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
		g.setColor(ground.getGroundColor());
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);

		if (isVisible()) {
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

		if (Settings.DRAW_TILE_BORDERS) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
		}

		if (isSelected()) {
			if (selections.get(0).getSelectionType() == SelectionType.TYPE_DESIGNATION) {
				g.setColor(Color.CYAN);
				g.drawRect(posX - 1, posY - 1, Settings.TILE_SIZE, Settings.TILE_SIZE);
			} else if (selections.get(0).getSelectionType() == SelectionType.TYPE_ZONE) {
				g.setColor(Color.CYAN);
				g.drawRect(posX - 1, posY - 1, Settings.TILE_SIZE, Settings.TILE_SIZE);
			}

		}
	}

	public void resetGround() {
		if (tileID == GameData.getTileID("tile_water")) {
			this.ground = new GroundWater();
		} else if (this.getZ() == 0) {
			this.ground = new GroundBlack();
		} else {
			this.ground = new GroundBlack();
		}
	}

}
