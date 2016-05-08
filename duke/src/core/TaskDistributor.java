package core;

import java.util.ArrayList;
import java.util.Random;

public class TaskDistributor {

	private Core core;
	private ArrayList<Task> taskList;
	private ArrayList<TaskGroupMining> taskGroups;

	public TaskDistributor(Core core) {
		this.core = core;
		taskList = new ArrayList<Task>();
		taskGroups = new ArrayList<TaskGroupMining>();
	}

	public void addTask(Task t) {
		taskList.add(t);
	}

	public void addTaskGroup(TaskGroupMining t) {
		taskGroups.add(t);
	}

	private Task getNextOpenTask() {
		for (Task t : taskList) {
			if (t.status == TaskStatus.OPEN)
				return t;
		}
		return null;
	}

	private void manageTaskGroups() {
		ArrayList<TaskGroupMining> toDelete = new ArrayList<TaskGroupMining>();
		for (TaskGroupMining taskGroup : taskGroups) {
			if (!taskGroup.isLocked()) {
				ArrayList nextTaskLayer = taskGroup.getNextTaskLayer();
				if (nextTaskLayer == null) {
					toDelete.add(taskGroup);
				} else {
					if (nextTaskLayer.get(0) instanceof TaskMoveAndMine) {
						for (Object o : nextTaskLayer) {
							taskList.add((TaskMoveAndMine) o);
							taskGroup.addLock();
						}
					}
				}
			}

		}
		taskGroups.removeAll(toDelete);
	}

	public void update() {
		manageTaskGroups();
		distributeOpenTasks();
		deleteSomeTasks();
	}

	private void distributeOpenTasks() {
		ArrayList<UnitWorker> availableUnits;
		Task t;
		UnitWorker u;

		availableUnits = core.getUnitManager().getAvailableWorkerUnits();
		if (availableUnits.size() == 0)
			return;
		t = getNextOpenTask();
		if (t == null)
			return;

		u = availableUnits.get(0);

		if (t.isReachableFor(u)) {
			u.setCurrentTask(t);
			t.setStatus(TaskStatus.ASSIGNED);
		} else {
			core.getUnitManager().lowerPrio(u);
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
