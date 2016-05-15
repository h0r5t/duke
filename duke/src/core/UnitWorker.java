package core;

public class UnitWorker extends Unit {

	private TaskPriorities taskPriorities;

	public UnitWorker(int x, int y, int z) {
		super(GameData.getUnitID("unit_worker"), x, y, z, 3);
		taskPriorities = new TaskPriorities();
	}

	public TaskPriorities getTaskPriorities() {
		return taskPriorities;
	}

}
