package core;

import java.util.ArrayList;

public class TaskManager {

	private Core core;
	private ArrayList<Task> taskList;

	public TaskManager(Core core) {
		this.core = core;
		taskList = new ArrayList<Task>();
	}

	public void addTask(Task t) {
		taskList.add(t);
	}

	public Task getNextOpenTask() {
		for (Task t : taskList) {
			if (t.status == TaskStatus.OPEN)
				return t;
		}

		return null;
	}

	public void update() {
		removeDoneTasks();
	}

	private void removeDoneTasks() {
		ArrayList<Task> toDelete = new ArrayList<Task>();
		for (Task t : taskList) {
			if (t.status == TaskStatus.DONE)
				toDelete.add(t);
		}

		for (Task t : toDelete) {
			taskList.remove(t);
		}
	}

}
