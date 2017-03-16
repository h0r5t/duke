package core;

import java.util.ArrayList;

public class TaskDistributor {

	private Core core;
	private ArrayList<Task> toAdd;
	private ArrayList<Coords3D> designatedCoords;
	private MultiMap<TaskType, Task> taskTypeMap;

	public TaskDistributor(Core core) {
		this.core = core;
		toAdd = new ArrayList<>();
		designatedCoords = new ArrayList<>();
		taskTypeMap = new MultiMap<>();
	}

	public void addDesignatedCoords(Coords3D c) {
		designatedCoords.add(c);
	}

	public ArrayList<Coords3D> getDesignatedCoords() {
		return designatedCoords;
	}

	public boolean isDesignated(Coords3D c) {
		return designatedCoords.contains(c);
	}

	public void addTask(Task t) {
		toAdd.add(t);
	}

	private void assignTasksToUnits() {
		for (UnitWorker u : core.getUnitManager().getAvailableWorkerUnits()) {
			for (TaskType type : u.getTaskPriorities().getPrios()) {
				ArrayList<Task> openTasks = (ArrayList<Task>) taskTypeMap.get(type);
				if (openTasks != null && openTasks.size() > 0) {
					Task t = openTasks.get(0);
					if (t.getStatus() == TaskStatus.OPEN)
						if (t.isReachableFor(u)) {
							assignTaskToUnit(t, u);
							break;
						}
				}
			}
		}
	}

	private void assignTaskToUnit(Task t, Unit u) {
		u.setCurrentTask(t);
		removeTaskFromHashMap(t);
	}

	public void update() {
		addTasks();
		distributeOpenTasks();
	}

	private void removeTaskFromHashMap(Task t) {
		taskTypeMap.removeOne(t.getType(), t);
	}

	private void addTaskToHashMap(Task t) {
		taskTypeMap.putOne(t.getType(), t);
	}

	private void addTasks() {
		for (Task t : toAdd) {
			addTaskToHashMap(t);
		}
		toAdd.clear();
	}

	private void distributeOpenTasks() {
		assignTasksToUnits();
	}

	public void wasMined(Coords3D c) {
		if (taskTypeMap.containsKey(TaskType.MINING))
			for (Task t : taskTypeMap.get(TaskType.MINING)) {
				TaskMiningWhole tm = (TaskMiningWhole) t;
				tm.wasMined(c);
			}
	}

}
