package core;

import java.awt.Graphics2D;

public class Cursor implements Visual {

	private Core core;
	private int xpos;
	private int ypos;
	private SelectionType selectionType;

	public Cursor(Core core, int x, int y) {
		this.core = core;
		xpos = x;
		ypos = y;
		selectionType = SelectionType.TYPE_CURSOR;
	}

	public void setSelectionType(SelectionType t) {
		this.selectionType = t;
	}

	public void resetSelectionType() {
		this.selectionType = SelectionType.TYPE_CURSOR;
	}

	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public int getZpos() {
		return core.getViewManager().getCurrentZ();
	}

	public void moveX(int x) {
		xpos += x;
		if (xpos < 0)
			xpos = 0;
		else if (xpos >= Settings.WORLD_WIDTH) {
			xpos = Settings.WORLD_WIDTH - 1;
		}
		core.getInputManager().cursorMoved();
	}

	public void moveY(int y) {
		ypos += y;
		if (ypos < 0)
			ypos = 0;
		else if (ypos >= Settings.WORLD_HEIGHT) {
			ypos = Settings.WORLD_HEIGHT - 1;
		}
		core.getInputManager().cursorMoved();
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(SelectionType.getColorForType(selectionType));
		g.drawRect(posX, posY, Settings.TILE_SIZE - 1, Settings.TILE_SIZE - 1);
	}

	public void setToTile(Tile tile) {
		xpos = tile.getX();
		ypos = tile.getY();
		core.getInputManager().cursorMoved();
	}

	public Coords3D getCoords3D() {
		return new Coords3D(xpos, ypos, getZpos());
	}

}
