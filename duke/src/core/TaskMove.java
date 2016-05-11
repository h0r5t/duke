package core;

import java.util.ArrayList;

public class TaskMove extends Task implements UsesPathFinderASync {

	private Path path;
	private int moveCooldown;
	private ArrayList<Coords3D> possibleTargets;

	public TaskMove(Coords3D target) {
		super(TaskType.MOVE);
		this.possibleTargets = new ArrayList<Coords3D>();
		possibleTargets.add(target);
	}

	public TaskMove(ArrayList<Coords3D> possibleTargets) {
		super(TaskType.MOVE);
		this.possibleTargets = possibleTargets;
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		if (possibleTargets.size() == 1) {
			return PathFinder.pathExists(unit.getCoords(),
					possibleTargets.get(0));
		} else {
			Coords3D bestTarget = PathFinder.findTargetTileWithShortestPath(
					unit.getTile().getCoords3D(), possibleTargets);
			if (bestTarget != null) {
				return true;
			} else {
				return false;
			}

		}
	}

	@Override
	public void update(Unit unit) {
		if (path == null) {
			Coords3D destination = PathFinder.findTargetTileWithShortestPath(
					unit.getTile().getCoords3D(), possibleTargets);
			path = PathFinder.findPath(unit.getCoords(), destination);
			if (path == null) {
				setStatus(TaskStatus.DONE);
				return;
			}
			moveCooldown = unit.getMoveSpeed();
		}
		moveCooldown--;
		if (moveCooldown == 0) {
			Coords3D c = path.popNext();
			if (c == null) {
				path = null;
				setStatus(TaskStatus.DONE);
				return;
			}
			Tile nextTile = c.getTile();
			unit.moveTo(nextTile.getX(), nextTile.getY(), nextTile.getZ());
			moveCooldown = unit.getMoveSpeed();
		}
	}

	@Override
	public void pathCallback(Path path) {
		// TODO Auto-generated method stub

	}
}
