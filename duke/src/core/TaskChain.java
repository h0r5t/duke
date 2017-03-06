package core;

import java.util.ArrayList;

public class TaskChain extends Task {

	protected ArrayList<Task> taskChain;

	public TaskChain(TaskType taskType) {
		super(taskType);
		taskChain = new ArrayList<Task>();
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return taskChain.get(0).isReachableFor(unit);
	}

	public void queueTask(Task t) {
		taskChain.add(t);
	}

	@Override
	public void update(Unit unit) {
		if (taskChain.isEmpty()) {
			setStatus(TaskStatus.DONE);
		} else {
			taskChain.get(0).update(unit);

			if (taskChain.get(0).getStatus() == TaskStatus.DONE) {
				taskChain.remove(0);
			}
		}
	}
}
