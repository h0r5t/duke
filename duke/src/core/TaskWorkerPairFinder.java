package core;

import java.util.ArrayList;

public class TaskWorkerPairFinder implements Runnable {

	private MultiMap<TaskType, Task> taskMap;
	private ArrayList<UnitWorker> availableWorkers;
	private ArrayList<TaskWorkerPair> taskWorkerPairs;
	private boolean done;

	public TaskWorkerPairFinder(MultiMap<TaskType, Task> taskMap, ArrayList<UnitWorker> availableWorkers) {
		done = false;
		this.taskMap = (MultiMap<TaskType, Task>) taskMap.clone();
		this.availableWorkers = (ArrayList<UnitWorker>) availableWorkers.clone();
		this.taskWorkerPairs = new ArrayList<>();
	}

	@Override
	public void run() {
		findOptimalPairs();
		done = true;
	}

	public boolean isDone() {
		return done;
	}

	public ArrayList<TaskWorkerPair> getOptimalPairs() {
		return taskWorkerPairs;
	}

	private void findOptimalPairs() {
		if (availableWorkers.size() == 0) {
			return;
		}

		for (TaskType type : taskMap.keySet()) {
			ArrayList<UnitWorker> workersWithTaskEnabled = getWorkersWithTaskEnabled(availableWorkers, type);
			if (workersWithTaskEnabled.size() > 0) {
				for (Task task : taskMap.get(type)) {
					int minDuration = Integer.MAX_VALUE;
					UnitWorker bestWorker = null;
					for (UnitWorker u : workersWithTaskEnabled) {
						if (!task.isReachableFor(u))
							continue;
						int duration = u.getEstimatedDurationToDoTask(task);
						if (duration < minDuration) {
							bestWorker = u;
							minDuration = duration;
						}
					}
					if (bestWorker != null) {
						taskWorkerPairs.add(new TaskWorkerPair(task, bestWorker));
						workersWithTaskEnabled.remove(bestWorker);
					}

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
