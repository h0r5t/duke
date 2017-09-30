package core;

public abstract class TaskActionCustom extends TaskAction {

	private Unit unit;

	public TaskActionCustom(TaskType type, int sleepTime) {
		super(type, sleepTime);
	}

	@Override
	public abstract void callback(Unit unit);

	@Override
	protected void doAction(Unit unit) {
		this.unit = unit;
		startTimer();
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}

}
