package core;

import java.util.ArrayList;

public class Path {

	private ArrayList<Coords3D> path;
	int counter = 0;
	private boolean isPossible;

	public Path(boolean isPossible) {
		path = new ArrayList<Coords3D>();
		this.isPossible = isPossible;
	}

	public void set(Path p) {
		if (p != null)
			path = p.getPathList();
		else
			isPossible = false;
	}

	public boolean isPossible() {
		return isPossible;
	}

	public void add(Coords3D t) {
		// first item is the source tile.
		if (counter == 0) {
			counter++;
			return;
		}
		path.add(t);
	}

	public ArrayList<Coords3D> getPathList() {
		return path;
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
