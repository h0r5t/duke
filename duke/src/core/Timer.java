package core;

public class Timer implements Runnable {

	private Callbackable callBack;
	private int milliseconds;
	private int context;

	public Timer(Callbackable callBack, int milliseconds, int context) {
		this.callBack = callBack;
		this.milliseconds = milliseconds;
		this.context = context;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		callBack.hiddenCallback(context);
	}

	public static void startTimer(Callbackable callBack, int milliseconds, int context) {
		new Thread(new Timer(callBack, milliseconds, context)).start();
	}

}
