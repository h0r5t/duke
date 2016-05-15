package core;

import java.util.HashMap;

public class TaskPriorities {

	private HashMap<TaskType, Integer> prios;

	public TaskPriorities() {
		prios = new HashMap<>();
		prios.put(TaskType.MINE, 2);
		prios.put(TaskType.HAUL, 1);
	}

	public int getPriorityForTask(Task t) {
		if (prios.containsKey(t.getType()))
			return prios.get(t.getType());
		else
			return 0;
	}

}
