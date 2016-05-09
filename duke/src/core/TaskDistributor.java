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
			ArrayList<TaskMoveAndMine> nextTasks = taskGroup.getNextTasks();
			if (nextTasks.size() == 0) {
				// toDelete.add(taskGroup);
			} else {
				taskList.addAll(nextTasks);
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

		u.setCurrentTask(t);
		t.setStatus(TaskStatus.ASSIGNED);
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
			taskInfo.add(String.valueOf(t.getType()).toLowerCase() + ", " + String.valueOf(t.getStatus()).toLowerCase()
					+ ", " + t.getTaskID());
		}

		return taskInfo;
	}

}
