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

	@Override
	public int hashCode() {
		return Integer.parseInt(x + "" + y + "" + z);
	}

	public Coords3D getRight() {
		return new Coords3D(x + 1, y, z);
	}

	public Coords3D getLeft() {
		return new Coords3D(x - 1, y, z);
	}

	public Coords3D getBottom() {
		return new Coords3D(x, y + 1, z);
	}

	public Coords3D getTop() {
		return new Coords3D(x, y - 1, z);
	}

	public Coords3D getTopLeft() {
		return new Coords3D(x - 1, y - 1, z);
	}

	public Coords3D getTopRight() {
		return new Coords3D(x + 1, y - 1, z);
	}

	public Coords3D getBottomLeft() {
		return new Coords3D(x - 1, y + 1, z);
	}

	public Coords3D getBottomRight() {
		return new Coords3D(x + 1, y + 1, z);
	}

	public Coords3D getBelow() {
		return new Coords3D(x, y, z + 1);
	}

	public double getDistance2D(Coords3D other) {
		int a = other.x - x;
		int b = other.y - y;
		return Math.sqrt(a * a + b * b);
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
