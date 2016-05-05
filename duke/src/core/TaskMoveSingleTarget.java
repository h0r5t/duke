package core;

public class TaskMoveSingleTarget extends Task {

	private Tile destinationTile;
	private TilePath tilePath;

	public TaskMoveSingleTarget(Tile to) {
		destinationTile = to;
	}

	@Override
	public TaskUnitPreference getUnitPreference() {
		return TaskUnitPreference.UNIT_CLOSEST;
	}

	public Tile getFirstDestinationTile() {
		return destinationTile;
	}

	@Override
	public void update(Unit unit) {
		if (tilePath == null) {
			tilePath = PathFinder.findPath(unit.getTile(), destinationTile);
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
