package core;

public class TaskWorkerPair {

	private Task t;
	private UnitWorker u;

	public TaskWorkerPair(Task t, UnitWorker u) {
		this.t = t;
		this.u = u;
	}

	public Task getTask() {
		return t;
	}

	public UnitWorker getWorker() {
		return u;
	}

}
