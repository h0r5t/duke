package core;

public class Coords3D {

	private int x;
	private int y;
	private int z;

	public Coords3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Coords3D) {
			Coords3D c = (Coords3D) other;
			return c.x == this.x && c.y == this.y && c.z == this.z;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Tile getTile() {
		return Core.getWorld().getTile(this);
	}

}
