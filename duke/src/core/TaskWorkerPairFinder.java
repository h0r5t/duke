package core;

import java.util.ArrayList;

public class TaskWorkerPairFinder implements Runnable {

	private Core core;
	private TaskDistributor taskDistributor;
	private ArrayList<TaskWorkerPair> taskWorkerPairs;
	private boolean done;

	public TaskWorkerPairFinder(Core core, TaskDistributor td) {
		this.core = core;
		this.taskDistributor = td;
		done = false;
	}

	@Override
	public void run() {
		while (true) {
			findOptimalPairs();
		}
	}

	public ArrayList<TaskWorkerPair> getOptimalPairs() {
		done = false;
		return taskWorkerPairs;
	}

	private void findOptimalPairs() {
		if (done)
			return;
		MultiMap<TaskType, Task> taskMap = taskDistributor.getTasks();
		ArrayList<UnitWorker> availableWorkers = new ArrayList<>(core.getUnitManager().getAvailableWorkerUnits());
		taskWorkerPairs = new ArrayList<>();

		if (availableWorkers.size() == 0)
			return;

		for (TaskType type : taskMap.keySet()) {
			ArrayList<UnitWorker> workersWithTaskEnabled = getWorkersWithTaskEnabled(availableWorkers, type);
			if (workersWithTaskEnabled.size() > 0) {
				for (Task task : taskMap.get(type)) {
					int minDuration = Integer.MAX_VALUE;
					UnitWorker bestWorker = null;
					for (UnitWorker u : workersWithTaskEnabled) {
						int duration = u.getEstimatedDurationToDoTask(task);
						if (duration < minDuration)
							bestWorker = u;
					}
					taskWorkerPairs.add(new TaskWorkerPair(task, bestWorker));
					availableWorkers.remove(bestWorker);
				}
			}
		}
	}

	private ArrayList<UnitWorker> getWorkersWithTaskEnabled(ArrayList<UnitWorker> list, TaskType taskType) {
		ArrayList<UnitWorker> returnList = new ArrayList<>();
		for (UnitWorker u : list) {
			if (u.getTaskPriorities().contains(taskType))
				returnList.add(u);
		}

		return returnList;
	}

}
