package core;

public class UnitWorker extends Unit {

	private TaskSet taskPriorities;
	private AIStrolling myAI;

	public UnitWorker(int x, int y, int z) {
		super(GameData.getUnitID("unit_worker"), x, y, z, 10);
		taskPriorities = new TaskSet();

		myAI = new AIStrolling(this);
		// new Thread(myAI).start();
	}

	public TaskSet getTaskPriorities() {
		return taskPriorities;
	}

	@Override
	public void update() {

	}

	@Override
	public void onDeath() {

	}

}
