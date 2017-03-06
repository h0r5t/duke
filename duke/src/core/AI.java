package core;

public abstract class AI implements Runnable {

	protected Unit unit;

	public AI(Unit unit) {
		this.unit = unit;
	}

	protected void runTask(Task t) {
		unit.setCurrentTask(t);
		while (true) {
			if (t.status == TaskStatus.DONE) {
				return;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void rest(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void moveTo(Coords3D coords) {
		runTask(new TaskMove(coords));
	}

	public abstract void doLogic();

	@Override
	public void run() {
		while (true) {
			doLogic();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
