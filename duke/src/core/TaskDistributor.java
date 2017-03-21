package core;

import java.util.ArrayList;

public class TaskDistributor {

	private Core core;
	private ArrayList<Task> toAdd;
	private MultiMap<TaskType, Task> taskTypeMap;
	private TaskWorkerPairFinder pairFinder;

	public TaskDistributor(Core core) {
		this.core = core;
		toAdd = new ArrayList<>();
		taskTypeMap = new MultiMap<>();
	}

	public void addTask(Task t) {
		toAdd.add(t);
	}

	private void assignPairs() {

		if (pairFinder == null) {
			pairFinder = new TaskWorkerPairFinder(taskTypeMap, core.getUnitManager().getAvailableWorkerUnits());
			new Thread(pairFinder).start();
		}

		if (pairFinder.isDone()) {
			ArrayList<TaskWorkerPair> pairs = pairFinder.getOptimalPairs();
			for (TaskWorkerPair pair : pairs) {
				assignTaskToUnit(pair.getTask(), pair.getWorker());
			}
			pairFinder = new TaskWorkerPairFinder(taskTypeMap, core.getUnitManager().getAvailableWorkerUnits());
			new Thread(pairFinder).start();
		}

	}

	public MultiMap<TaskType, Task> getTasks() {
		return taskTypeMap;
	}

	private void assignTaskToUnit(Task t, Unit u) {
		u.setCurrentTask(t);
		t.setStatus(TaskStatus.ASSIGNED);
		removeTaskFromHashMap(t);
	}

	public void update() {
		addTasks();
		assignPairs();
	}

	public void removeTaskFromHashMap(Task t) {
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

	public void wasMined(Coords3D c) {
		if (taskTypeMap.containsKey(TaskType.MINING))
			for (Task t : taskTypeMap.get(TaskType.MINING)) {
				TaskMiningWhole tm = (TaskMiningWhole) t;
				tm.wasMined(c);
			}
	}

}
