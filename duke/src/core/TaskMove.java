package core;

import java.util.ArrayList;

public class TaskMove extends Task {

	private Path path;
	private FutureContainer<Path> pathFuture;
	private FutureContainer<Coords3D> coordsFuture;
	private ArrayList<Coords3D> possibleTargets;
	private boolean pathFindingStarted = false;

	public TaskMove(Coords3D target) {
		super(TaskType.MOVING);
		this.possibleTargets = new ArrayList<Coords3D>();
		possibleTargets.add(target);
	}

	@Override
	public double getDistance2D(Unit unit) {
		return PathFinder.estimateDistance2D(unit.getCoords(), possibleTargets.get(0));
	}

	public TaskMove(ArrayList<Coords3D> possibleTargets) {
		super(TaskType.MOVING);
		this.possibleTargets = possibleTargets;
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}

	@Override
	public void update(Unit unit) {
		if (coordsFuture == null) {
			coordsFuture = PathFinderASync.findTargetTileWithShortestPath(unit.getTile().getCoords3D(),
					possibleTargets);
		} else {
			if (coordsFuture.isReady()) {
				if (!pathFindingStarted) {

					pathFuture = PathFinderASync.findPath(unit.getCoords(), coordsFuture.getContained());
					pathFindingStarted = true;
				}
				if (pathFindingStarted && pathFuture.isReady()) {
					path = pathFuture.getContained();
					if (!path.isPossible()) {
						setStatus(TaskStatus.DONE);
						return;
					} else {
						if (!unit.isMoving()) {
							Coords3D c = path.popNext();
							if (c == null) {
								path = null;
								setStatus(TaskStatus.DONE);
								return;
							}
							Tile nextTile = c.getTile();
							unit.moveTo(nextTile.getX(), nextTile.getY(), nextTile.getZ());
						}
					}
				}
			}
		}
	}
}
