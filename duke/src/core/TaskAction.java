package core;

public abstract class TaskAction extends Task implements Callbackable {

	private boolean wasCalled;
	private boolean callbackCalled;
	private int callbackContext;

	public TaskAction() {
		wasCalled = false;
		callbackCalled = false;
	}

	@Override
	public void update(Unit unit) {
		if (!wasCalled) {
			doAction(unit);
			wasCalled = true;
		}

		if (callbackCalled) {
			callback(callbackContext);
			callbackCalled = false;
		}

	}

	protected abstract void doAction(Unit unit);

	protected void startTimer(int milliseconds, int context) {
		Timer.startTimer(this, milliseconds, context);
		callbackContext = context;
	}

	@Override
	public void hiddenCallback(int context) {
		callbackCalled = true;
	}

}
