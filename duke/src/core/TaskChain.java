package core;

import java.util.ArrayList;

public class TaskChain extends Task {

	protected ArrayList<Task> taskChain;

	public TaskChain(TaskType taskType) {
		super(taskType);
		taskChain = new ArrayList<Task>();
	}

	@Override
	public double getDistance2D(Unit unit) {
		for (Task t : taskChain) {
			if (t.getDistance2D(unit) > 0) {
				return t.getDistance2D(unit);
			}
		}
		return 0;
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
			} else if (taskChain.get(0).getStatus() == TaskStatus.FAILED) {
				setStatus(TaskStatus.FAILED);
			}
		}
	}

	@Override
	public int getEstimatedTimeNeeded(Unit u) {
		int sum = 0;
		for (Task t : taskChain) {
			sum += t.getEstimatedTimeNeeded(u);
		}
		return sum;
	}
}
