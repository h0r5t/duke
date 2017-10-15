package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import pathfinder.GraphNode;

public abstract class Tile extends GraphNode implements Drawable {

	protected int tileID;
	protected Ground ground;
	private boolean isVisible;
	private boolean isExposed;
	private ArrayList<Zone2D> selections;
	private ArrayList<Projectile> projectiles;
	private Fluid fluid;

	public Tile(int tileID, int x, int y, int z) {
		super(UniqueIDFactory.getID(), x, y, z);
		this.tileID = tileID;
		this.selections = new ArrayList<Zone2D>();
		this.projectiles = new ArrayList<Projectile>();
		this.fluid = null;
		this.isVisible = false;
	}

	public void initAfterWorldGeneration() {
		if (getDefaultGround() != null)
			setGround(getDefaultGround());
	}

	public boolean isAir() {
		return this instanceof TileAir;
	}

	public boolean needsRamp() {
		return false;
	}

	public Ground getDefaultGround() {
		return null;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public Ground getGround() {
		return ground;
	}

	public ItemDrop getDrop() {
		return null;
	}

	public boolean canBeMined() {
		return false;
	}

	public boolean isExposed() {
		return isExposed;
	}

	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
		Core.getEventHub().fireEvent(new EventTileExposureChanged(this));
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

	public boolean blocksPath() {
		if (isSolid())
			return true;
		if (Core.getWorld() == null)
			return false;
		// if (Core.getWorld().getUnitsAt(this) != null)
		// return true;
		for (Item i : Core.getWorld().getItemsAt(this.getCoords3D())) {
			if (i.blocksPath())
				return true;
		}
		return false;
	}

	public abstract boolean isSolid();

	public boolean overridesOldGround() {
		return false;
	}

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

	public Tile getLeft() {
		return getCoords3D().getLeft().getTile();
	}

	public Tile getRight() {
		return getCoords3D().getRight().getTile();
	}

	public Tile getTop() {
		return getCoords3D().getTop().getTile();
	}

	public Tile getBottom() {
		return getCoords3D().getBottom().getTile();
	}

	public void draw(Graphics2D g, int posX, int posY) {
		if (this instanceof TileAir)
			return;

		if (ground != null) {
			if (ground instanceof GroundAir) {
				for (int i = 1; i < Settings.DRAW_DARKER_LEVELS_AMOUNT; i++) {
					Tile tileBelow = Core.getWorld().getTile((int) super.x(), (int) super.y(), (int) super.z() + i);
					if (!(tileBelow.getGround() instanceof GroundAir)) {
						tileBelow.getGround().draw(g, posX, posY);
						break;
					}
				}
			} else
				ground.draw(g, posX, posY);
		}

		if (isExposed) {
			TextureStore.getTileTexture(tileID).draw(g, posX, posY);
		} else {
			TextureStore.getDarknessBackgroundTexture().draw(g, posX, posY);
		}

		// draw fluid
		if (fluid != null)
			fluid.draw(g, posX, posY);

		if (Settings.DRAW_TILE_BORDERS) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);
		}

		if (isSelected()) {
			g.setColor(SelectionType.getColorForType(selections.get(0).getSelectionType()));
			g.drawRect(posX - 1, posY - 1, Settings.TILE_SIZE, Settings.TILE_SIZE);
		}
	}

}
