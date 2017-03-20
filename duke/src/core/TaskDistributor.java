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
		pairFinder = new TaskWorkerPairFinder(core, this);
		new Thread(pairFinder).start();
	}

	public void addTask(Task t) {
		toAdd.add(t);
	}

	private void assignPairs() {
		ArrayList<TaskWorkerPair> pairs = pairFinder.getOptimalPairs();
		for (TaskWorkerPair pair : pairs) {
			assignTaskToUnit(pair.getTask(), pair.getWorker());
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
		distributeOpenTasks();
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

	private void distributeOpenTasks() {
		assignPairs();
	}

	public void wasMined(Coords3D c) {
		if (taskTypeMap.containsKey(TaskType.MINING))
			for (Task t : taskTypeMap.get(TaskType.MINING)) {
				TaskMiningWhole tm = (TaskMiningWhole) t;
				tm.wasMined(c);
			}
	}

}
