package core;

public abstract class Task {

	protected TaskStatus status = TaskStatus.OPEN;

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public abstract void update(Unit unit);

}
