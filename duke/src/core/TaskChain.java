package core;

import java.util.ArrayList;

public class TaskChain extends Task {

	protected ArrayList<Task> taskChain;
	protected boolean pickedUp = false;

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
		if (pickedUp == false) {
			pickedUp = true;
			unit.setCurrentTask(taskChain.get(0));
			unit.setActiveTaskChain(this);
		}
		if (taskChain.get(0).getStatus() == TaskStatus.DONE) {
			taskChain.remove(0);
			if (taskChain.size() == 0) {
				setStatus(TaskStatus.DONE);
				unit.setActiveTaskChain(null);
			} else {
				unit.setCurrentTask(taskChain.get(0));
			}
		}
	}
}
