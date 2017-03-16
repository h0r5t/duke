package core;

import java.util.ArrayList;

public abstract class Task {

	protected TaskStatus status = TaskStatus.OPEN;
	private TaskType myType;
	private int taskID;

	public Task(TaskType taskType) {
		myType = taskType;
		taskID = UniqueIDFactory.getID();
	}

	public double getDistance2D(Unit unit) {
		return 0;
	}

	public TaskType getType() {
		return myType;
	}

	public abstract boolean isReachableFor(Unit unit);

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public abstract void update(Unit unit);

	public int getTaskID() {
		return taskID;
	}

	protected ArrayList<Coords3D> getPossibleTargets(Coords3D targetToMine) {
		int x = targetToMine.getX();
		int y = targetToMine.getY();

		ArrayList<Coords3D> possibleTargets = new ArrayList<Coords3D>();
		Tile t1 = Core.getWorld().getTile(x + 1, y, targetToMine.getZ());
		Tile t2 = Core.getWorld().getTile(x - 1, y, targetToMine.getZ());
		Tile t3 = Core.getWorld().getTile(x, y + 1, targetToMine.getZ());
		Tile t4 = Core.getWorld().getTile(x, y - 1, targetToMine.getZ());
		possibleTargets.add(t1.getCoords3D());
		possibleTargets.add(t2.getCoords3D());
		possibleTargets.add(t3.getCoords3D());
		possibleTargets.add(t4.getCoords3D());

		return possibleTargets;
	}

}
