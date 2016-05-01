package core;

public class TaskMove extends Task {

	private Core core;
	private Tile destinationTile;
	private TilePath tilePath;

	public TaskMove(Core core, Tile to) {
		this.core = core;
		destinationTile = to;
	}

	@Override
	public void update(Unit unit) {
		if (tilePath == null) {
			tilePath = core.getPathFinder().findPath(unit.getTile(),
					destinationTile);
			if (tilePath == null)
				setStatus(TaskStatus.DONE);
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
