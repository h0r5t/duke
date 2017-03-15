package core;

import java.util.ArrayList;

public class TaskMiningWhole extends TaskChain {

	private ArrayList<Coords3D> miningCoords;
	private ArrayList<TaskMining> tasks;
	private boolean isReachable = false;
	private boolean locked = false;

	public TaskMiningWhole(Zone2D zone) {
		super(TaskType.MINING);
		miningCoords = zone.getCoords();
		tasks = new ArrayList<>();
		releaseFirstTasks(zone);
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return isReachable;
	}

	private void releaseFirstTasks(Zone2D zone) {
		ArrayList<Coords3D> added = new ArrayList<>();
		for (Coords3D c : miningCoords) {
			if (!c.getTile().canBeMined()) {
				c.getTile().deselect(zone);
				added.add(c);
				continue;
			}
			releaseTaskIfReachable(c, added);
		}
		for (Coords3D c : added) {
			miningCoords.remove(c);
		}
	}

	private void releaseTaskIfReachable(Coords3D c, ArrayList<Coords3D> added) {
		if (PathFinder.shouldBeReachableSurrounding(c)) {
			tasks.add(new TaskMining(c, this));
			added.add(c);
			isReachable = true;
		}
	}

	private void releaseTaskIfReachable(Coords3D c) {
		if (PathFinder.shouldBeReachableSurrounding(c)) {
			tasks.add(new TaskMining(c, this));
			miningCoords.remove(c);
			isReachable = true;
		}
	}

	private void assignNewTask(Unit u) {
		if (tasks.size() > 0) {
			double minDistance = Integer.MAX_VALUE;
			TaskMining nearestTask = null;
			for (TaskMining t : tasks) {
				double distance = PathFinder.estimateDistance2D(u.getCoords(), t.getMiningTarget());
				if (distance < minDistance) {
					minDistance = distance;
					nearestTask = t;
				}
			}
			queueTask(nearestTask);
			tasks.remove(nearestTask);
		}
	}

	@Override
	public void update(Unit unit) {
		if (taskChain.isEmpty()) {
			if (!locked)
				assignNewTask(unit);
		} else {
			taskChain.get(0).update(unit);
			if (taskChain.get(0).getStatus() == TaskStatus.DONE) {
				taskChain.remove(0);
			}
		}
		if (miningCoords.isEmpty() && tasks.isEmpty() && taskChain.isEmpty()) {
			setStatus(TaskStatus.DONE);
		}
	}

	public void taskEnded(TaskMining t) {

		locked = true;

		new Thread(new Runnable() {

			@Override
			public void run() {
				Coords3D left = t.getMiningTarget().getLeft();
				if (miningCoords.contains(left)) {
					releaseTaskIfReachable(left);
				}
				Coords3D right = t.getMiningTarget().getRight();
				if (miningCoords.contains(right)) {
					releaseTaskIfReachable(right);
				}
				Coords3D top = t.getMiningTarget().getTop();
				if (miningCoords.contains(top)) {
					releaseTaskIfReachable(top);
				}
				Coords3D bot = t.getMiningTarget().getBottom();
				if (miningCoords.contains(bot)) {
					releaseTaskIfReachable(bot);
				}
				locked = false;
			}
		}).start();

	}

	public void wasMined(Coords3D c) {
		// called when a coords was mined on the map
		taskEnded(new TaskMining(c, this));
	}

}
