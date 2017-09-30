package core;

public abstract class TaskAction extends Task implements Callbackable {

	private boolean wasCalled;
	private boolean callbackCalled;
	private int callbackContext;
	private int sleepTime;

	public TaskAction(TaskType type, int sleepTime) {
		super(type);
		wasCalled = false;
		callbackCalled = false;
		this.sleepTime = sleepTime;
	}

	@Override
	public void update(Unit unit) {
		if (!wasCalled) {
			doAction(unit);
			wasCalled = true;
		}

		if (callbackCalled) {
			callback(unit);
			callbackCalled = false;
			setStatus(TaskStatus.DONE);
		}
	}

	@Override
	public int getEstimatedTimeNeeded(Unit u) {
		return sleepTime;
	}

	protected abstract void doAction(Unit unit);

	protected void startTimer() {
		Timer.startTimer(this, sleepTime, 0);
		callbackContext = 0;
	}

	@Override
	public void hiddenCallback(int context) {
		callbackCalled = true;
	}

}
