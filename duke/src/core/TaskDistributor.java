package core;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskDistributor {

	private Core core;
	private CopyOnWriteArrayList<Task> taskList;

	public TaskDistributor(Core core) {
		this.core = core;
		taskList = new CopyOnWriteArrayList<Task>();
	}

	public void addTask(Task t) {
		taskList.add(t);
	}

	private Task getNextOpenTask() {
		for (Task t : taskList) {
			if (t.status == TaskStatus.OPEN)
				return t;
		}
		return null;
	}

	public void update() {
		distributeTasks();
		deleteSomeTasks();
		sortOpenTasks();
	}

	private void sortOpenTasks() {

	}

	private void distributeTasks() {
		ArrayList<UnitWorker> availableUnits = core.getUnitManager()
				.getAvailableWorkerUnits();
		if (availableUnits.size() == 0)
			return;
		Task t = getNextOpenTask();
		if (t == null)
			return;

		UnitWorker u = availableUnits.get(0);
		if (t.isReachableFor(u)) {
			u.setCurrentTask(t);
			t.setStatus(TaskStatus.ASSIGNED);
		} else {
			// move unit and task to bottom of their lists.
			// core.getUnitManager().lowerPrio(u);
			taskList.remove(t);
			taskList.add(t);
		}

	}

	private void deleteSomeTasks() {
		if (taskList.size() == 0)
			return;
		int i = new Random().nextInt(taskList.size());
		Task t = taskList.get(i);
		if (t.status == TaskStatus.DONE) {
			taskList.remove(i);
		}
	}

	public ArrayList<String> getTasksInfoList() {
		ArrayList<String> taskInfo = new ArrayList<String>();
		for (Task t : taskList) {
			taskInfo.add(String.valueOf(t.getType()).toLowerCase() + ", "
					+ String.valueOf(t.getStatus()).toLowerCase() + ", "
					+ t.getTaskID());
		}

		return taskInfo;
	}

}
