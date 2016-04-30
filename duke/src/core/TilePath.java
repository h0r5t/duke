package core;

import java.util.ArrayList;

public class TilePath {

	private Core core;
	private ArrayList<Tile> path;
	private ArrayList<TilePathMarker> markers;
	int counter = 0;

	public TilePath(Core core) {
		this.core = core;
		path = new ArrayList<Tile>();
		markers = new ArrayList<TilePathMarker>();
	}

	public void add(Tile t) {
		// first item is the source tile.
		if (counter == 0) {
			counter++;
			return;
		}
		path.add(t);
		TilePathMarker marker = new TilePathMarker(t.getX(), t.getY());
		marker.addMarkersToVisualsMap(core.getViewManager());
		markers.add(marker);

	}

	public void destroy() {
		for (TilePathMarker marker : markers) {
			marker.removeMarkersFromVisualsMap(core.getViewManager());
		}
	}
}
