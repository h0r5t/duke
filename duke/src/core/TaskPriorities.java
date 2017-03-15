package core;

import java.util.ArrayList;

public class TaskPriorities {

	private ArrayList<TaskType> prios;

	public TaskPriorities() {
		prios = new ArrayList<TaskType>();
		prios.add(TaskType.MINING);
		prios.add(TaskType.BUILDING);
		prios.add(TaskType.HAULING);
		prios.add(TaskType.MOVING);
	}

	public ArrayList<TaskType> getPrios() {
		return prios;
	}

}
