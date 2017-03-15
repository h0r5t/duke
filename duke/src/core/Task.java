package core;

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

}
