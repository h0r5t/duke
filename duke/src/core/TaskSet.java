package core;

import java.util.ArrayList;

public class TaskSet {

	private ArrayList<TaskType> set;

	public TaskSet() {
		set = new ArrayList<TaskType>();
		set.add(TaskType.MINING);
		set.add(TaskType.TREE_CHOPPING);
		set.add(TaskType.BUILDING);
		set.add(TaskType.HAULING);
		set.add(TaskType.MOVING);
	}

	public ArrayList<TaskType> getTaskSet() {
		return set;
	}

	public boolean contains(TaskType t) {
		if (set.contains(t))
			return true;
		return false;
	}

}
