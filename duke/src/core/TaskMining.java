package core;

import java.util.ArrayList;

public class TaskMining extends TaskChain {

	private Coords3D miningTarget;
	private TaskMiningWhole myTaskWhole;

	public TaskMining(Coords3D targetToMine, TaskMiningWhole taskMiningWhole) {
		super(TaskType.MINING);
		this.miningTarget = targetToMine;
		this.myTaskWhole = taskMiningWhole;
		TaskMove moveTask = new TaskMove(getPossibleTargets(targetToMine));
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

	private ArrayList<Coords3D> getPossibleTargets(Coords3D targetToMine) {
		int x = targetToMine.getX();
		int y = targetToMine.getY();

		ArrayList<Coords3D> possibleTargets = new ArrayList<Coords3D>();
		Tile t1 = Core.getWorld().getTile(x + 1, y, targetToMine.getZ());
		Tile t2 = Core.getWorld().getTile(x - 1, y, targetToMine.getZ());
		Tile t3 = Core.getWorld().getTile(x, y + 1, targetToMine.getZ());
		Tile t4 = Core.getWorld().getTile(x, y - 1, targetToMine.getZ());
		possibleTargets.add(t1.getCoords3D());
		possibleTargets.add(t2.getCoords3D());
		possibleTargets.add(t3.getCoords3D());
		possibleTargets.add(t4.getCoords3D());

		return possibleTargets;
	}

}
