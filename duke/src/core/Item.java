package core;

import java.awt.Graphics2D;

public abstract class Item implements Visual {

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

	public abstract boolean collides();

	public int getItemID() {
		return itemID;
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		TextureStore.getItemTexture(itemID, myChar).draw(g, posX, posY, 0);
	}

}
