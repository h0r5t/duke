package core;

public abstract class Unit implements Visual {

	protected int x;
	protected int y;
	protected int id;

	public Unit(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void moveTo(int xpos, int ypos) {
		Core.getWorld().moveUnit(this, x, y, xpos, ypos);
		this.x = xpos;
		this.y = ypos;
	}
}
