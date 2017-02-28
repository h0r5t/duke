package core;

public class UnitWorker extends Unit {

	private TaskPriorities taskPriorities;
	private AIStrolling myAI;

	public UnitWorker(int x, int y, int z) {
		super(GameData.getUnitID("unit_worker"), x, y, z, 16);
		taskPriorities = new TaskPriorities();

		myAI = new AIStrolling(this);
		new Thread(myAI).start();
	}

	public TaskPriorities getTaskPriorities() {
		return taskPriorities;
	}

	@Override
	public void update() {

	}

	@Override
	public void onDeath() {

	}

}
