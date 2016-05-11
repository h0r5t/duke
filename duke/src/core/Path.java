package core;

import java.util.ArrayList;

public class Path {

	private ArrayList<Coords3D> path;
	int counter = 0;
	private boolean isNull;

	public Path() {
		path = new ArrayList<Coords3D>();
	}

	public Path(boolean isNull) {
		this.isNull = isNull;
	}

	public boolean isNull() {
		return isNull;
	}

	public void add(Coords3D t) {
		// first item is the source tile.
		if (counter == 0) {
			counter++;
			return;
		}
		path.add(t);
	}

	public int getPathLength() {
		return path.size();
	}

	public Coords3D popNext() {
		if (path.size() == 0)
			return null;
		Coords3D t = path.get(0);
		path.remove(0);
		return t;
	}
}
