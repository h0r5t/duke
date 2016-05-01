package core;

import java.util.ArrayList;

public class TilePath {

	private Core core;
	private ArrayList<Tile> path;
	int counter = 0;

	public TilePath(Core core) {
		this.core = core;
		path = new ArrayList<Tile>();
	}

	public void add(Tile t) {
		// first item is the source tile.
		if (counter == 0) {
			counter++;
			return;
		}
		path.add(t);

	}
}
