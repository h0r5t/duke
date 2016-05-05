package core;

import java.util.ArrayList;

public class TaskDistributor {

	private Core core;
	private ArrayList<Task> taskList;

	public TaskDistributor(Core core) {
		this.core = core;
		taskList = new ArrayList<Task>();
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
		removeDoneTasks();
	}

	private void distributeTasks() {
		ArrayList<Unit> availableUnits = core.getUnitManager().getAvailableUnits();
		if (availableUnits.size() == 0)
			return;
		Task t = getNextOpenTask();
		if (t != null)
			if (t.getUnitPreference() == TaskUnitPreference.UNIT_ANY)
				availableUnits.get(0).setCurrentTask(t);
			else if (t.getUnitPreference() == TaskUnitPreference.UNIT_CLOSEST) {
				Unit closest = PathFinder.findUnitWithShortestPath(availableUnits, t.getFirstDestinationTile());
				if (closest != null)
					closest.setCurrentTask(t);
			}
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
