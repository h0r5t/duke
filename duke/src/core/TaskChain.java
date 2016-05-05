package core;

import java.util.ArrayList;

public class TaskChain extends Task {

	private ArrayList<Task> taskChain;
	private boolean pickedUp = false;

	public TaskChain() {
		taskChain = new ArrayList<Task>();
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
				return;
			}
			unit.setCurrentTask(taskChain.get(0));
		}
	}

}
