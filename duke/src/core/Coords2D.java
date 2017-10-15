package core;

public class Coords2D {

	private int x;
	private int y;

	public Coords2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return new String(x + "," + y).hashCode();
	}

}
