package core;

import java.awt.Graphics2D;

public abstract class Item {

	private int itemID;
	private Character myChar;

	public Item(int itemID) {
		this.itemID = itemID;
		getChar();
	}

	public abstract String getName();

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

	protected void getChar() {
		myChar = GameData.getRandomItemCharacter(getItemID());
	}

	public abstract boolean blocksPath();

	public int getItemID() {
		return itemID;
	}

	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		TextureStore.getItemTexture(itemID, myChar).draw(g, posX, posY, darkerLevel);
	}

}
