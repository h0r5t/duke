package core;

public class TaskActionBusy extends TaskAction {

	private int timeInMs;
	private SimpleCallback callback;

	public TaskActionBusy(int timeInMs, SimpleCallback callback) {
		super(TaskType.BUSY);
		this.timeInMs = timeInMs;
		this.callback = callback;
	}

	@Override
	public void callback(int context) {
		callback.callback();
		setStatus(TaskStatus.DONE);
	}

	@Override
	protected void doAction(Unit unit) {
		startTimer(timeInMs, 0);
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}

}
