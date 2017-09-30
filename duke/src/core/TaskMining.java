package core;

public class TaskMining extends TaskChain {

	private Coords3D miningTarget;
	private TaskMiningWhole myTaskWhole;

	public TaskMining(Coords3D targetToMine, TaskMiningWhole taskMiningWhole) {
		super(TaskType.MINING);
		this.miningTarget = targetToMine;
		this.myTaskWhole = taskMiningWhole;
		TaskMove moveTask = new TaskMove(getReachableSurroundingTiles(targetToMine));
		queueTask(moveTask);

		TaskActionMine miningTask = new TaskActionMine(targetToMine);
		queueTask(miningTask);
	}

	public int estimateTimeLeft(Unit u) {
		if (taskChain.size() == 0)
			return 0;
		else if (taskChain.get(0) instanceof TaskMove) {
			return PathFinder.estimateTimeNeeded(u, getMiningTarget()) + 1000;
		} else
			return 1000;
	}

	public int estimateTimeNeededForUnit(Unit u) {
		return PathFinder.estimateTimeNeeded(u, getMiningTarget()) + 1000;
	}

	public Coords3D getMiningTarget() {
		return miningTarget;
	}

	@Override
	public void update(Unit unit) {
		if (taskChain.isEmpty()) {
			setStatus(TaskStatus.DONE);
			return;
		}

		taskChain.get(0).update(unit);

		if (taskChain.get(0).getStatus() == TaskStatus.DONE) {
			taskChain.remove(0);
			if (taskChain.isEmpty()) {
				setStatus(TaskStatus.DONE);
				// report to group
				myTaskWhole.taskEnded(this);

			}
		}
	}

	@Override
	public void onPickUp(Unit u) {

	}

}
