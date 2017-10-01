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

	public abstract int getEstimatedTimeNeeded(Unit u);

	public TaskType getType() {
		return myType;
	}

	protected ArrayList<Coords3D> getReachableSurroundingTiles(Coords3D coords3d) {
		return Core.getWorld().getReachableSurroundingTiles(coords3d);
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

}
