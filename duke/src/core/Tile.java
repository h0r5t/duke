package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import pathfinder.GraphNode;

public abstract class Tile extends GraphNode {

	protected Character myChar;
	protected int tileID;
	protected Ground ground;
	private boolean isVisible;
	private ArrayList<Zone2D> selections;
	private ArrayList<Projectile> projectiles;
	private Fluid fluid;

	public Tile(int tileID, int x, int y, int z) {
		super(UniqueIDFactory.getID(), x, y, z);
		this.tileID = tileID;
		this.selections = new ArrayList<Zone2D>();
		this.projectiles = new ArrayList<Projectile>();
		this.fluid = null;
		getChar();

		setVisible(true);
	}

	public Ground getDefaultGround() {
		return new GroundBlack();
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public Ground getGround() {
		return ground;
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

	public void setFluid(Fluid f) {
		fluid = f;
		if (fluid != null)
			fluid.setCoords(getCoords3D());
	}

	public Fluid getFluid() {
		return fluid;
	}

	public boolean hasFluid() {
		return fluid != null;
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

	public void drawBorders(Graphics2D g, int posX, int posY, int darkerLevel) {
		if (Settings.DRAW_BORDERS) {
			int strokeNum = 0;
			if (darkerLevel == 0 || darkerLevel == Settings.DRAW_DARKER_LEVELS_AMOUNT - 1)
				strokeNum = 1;

			if (getCoords3D().getLeft().getTile() instanceof TileAir) {
				TextureStore.getBorderTexture(BorderLocation.LEFT, strokeNum).draw(g, posX, posY, 0);
			}
			if (getCoords3D().getRight().getTile() instanceof TileAir) {
				TextureStore.getBorderTexture(BorderLocation.RIGHT, strokeNum).draw(g, posX, posY, 0);
			}
			if (getCoords3D().getTop().getTile() instanceof TileAir) {
				TextureStore.getBorderTexture(BorderLocation.TOP, strokeNum).draw(g, posX, posY, 0);
			}
			if (getCoords3D().getBottom().getTile() instanceof TileAir) {
				TextureStore.getBorderTexture(BorderLocation.BOTTOM, strokeNum).draw(g, posX, posY, 0);
			}
		}
	}

	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		ground.draw(g, posX, posY, darkerLevel);

		if (this instanceof TileAir)
			return;

		if (isVisible()) {
			TextureStore.getTileTexture(tileID, myChar).draw(g, posX, posY, darkerLevel);
		}

		// draw fluid
		if (fluid != null)
			fluid.draw(g, posX, posY, darkerLevel);

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

}
