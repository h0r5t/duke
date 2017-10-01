package core;

import java.awt.Graphics2D;

public abstract class Item {

	private int itemID;
	private Character myChar;
	private boolean isClaimed;
	private boolean isVisible;

	public Item(int itemID) {
		this.itemID = itemID;
		this.isVisible = true;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public abstract String getName();

	public abstract int getVolume();

	public Coords3D getCoords3D() {
		return Core.getWorld().getItemPos(this);
	}

	public void moveTo(Coords3D c) {
		Core.getWorld().removeItem(this);
		Core.getWorld().addItem(this, c);
	}

	public void destroy() {
		Core.getWorld().removeItem(this);
	}

	public boolean isClaimed() {
		return isClaimed;
	}

	public void setClaimed(boolean isClaimed) {
		this.isClaimed = isClaimed;
	}

	public abstract boolean blocksPath();

	public int getItemID() {
		return itemID;
	}

	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		TextureStore.getItemTexture(itemID).draw(g, posX, posY, darkerLevel);
	}

}
