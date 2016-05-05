package core;

import java.util.ArrayList;

public class TaskMoveMultipleTargets extends Task {

	private ArrayList<Tile> destinationTiles;
	private TilePath tilePath;

	public TaskMoveMultipleTargets(ArrayList<Tile> to) {
		destinationTiles = to;
	}

	@Override
	public void update(Unit unit) {
		if (tilePath == null) {
			Tile bestTile = PathFinder.findTargetTileWithShortestPath(unit.getTile(), destinationTiles);
			tilePath = PathFinder.findPath(unit.getTile(), bestTile);
			if (tilePath == null) {
				setStatus(TaskStatus.DONE);
				return;
			}
		}
		Tile nextTile = tilePath.popNext();
		if (nextTile == null) {
			tilePath = null;
			setStatus(TaskStatus.DONE);
			return;
		}
		unit.moveTo(nextTile.getX(), nextTile.getY(), nextTile.getZ());
	}

}
