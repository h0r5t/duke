package core;

import java.awt.Graphics2D;

public class Cursor implements Drawable {

	private Core core;
	private int xpos;
	private int ypos;
	private int xdelay;
	private int ydelay;
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
		if (Math.abs(x) == 1)
			xdelay = x * Settings.TILE_SIZE;
		xpos += x;
		if (xpos < 0)
			xpos = 0;
		else if (xpos >= Settings.WORLD_WIDTH) {
			xpos = Settings.WORLD_WIDTH - 1;
		}
		core.getInputManager().cursorMoved();
	}

	public void moveY(int y) {
		if (Math.abs(y) == 1)
			ydelay = y * Settings.TILE_SIZE;
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
		int drawPosX = posX - xdelay;
		int drawPosY = posY - ydelay;
		g.setColor(SelectionType.getColorForType(selectionType));
		g.drawRect(drawPosX, drawPosY, Settings.TILE_SIZE - 1, Settings.TILE_SIZE - 1);

		if (xdelay > 0) {
			xdelay -= 1;
		} else if (xdelay < 0)
			xdelay += 1;

		if (ydelay > 0) {
			ydelay -= 1;
		} else if (ydelay < 0)
			ydelay += 1;

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
