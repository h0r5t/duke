package core;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskGroupMining implements Runnable {

	private TaskMoveAndMine[][] points;
	private ArrayList<TaskMoveAndMine> all;
	private CopyOnWriteArrayList<TaskMoveAndMine> ended;
	private ArrayList<TaskMoveAndMine> toRelease;
	private int xstart;
	private int ystart;
	private int width;
	private int height;
	private boolean sorted = false;

	public TaskGroupMining(Zone2D area) {
		this.xstart = area.getX();
		this.ystart = area.getY();
		this.width = area.getWidth() + 1;
		this.height = area.getHeight() + 1;
		points = new TaskMoveAndMine[this.width][this.height];
		all = new ArrayList<TaskMoveAndMine>();
		ended = new CopyOnWriteArrayList<TaskMoveAndMine>();
		toRelease = new ArrayList<TaskMoveAndMine>();

		for (Coords3D c : area.getCoords()) {
			if (!c.getTile().canBeMined()) {
				c.getTile().deselect(area);
				continue;
			}
			points[c.getX() - xstart][c.getY() - ystart] = new TaskMoveAndMine(c);
			points[c.getX() - xstart][c.getY() - ystart].setTaskGroup(this);
			all.add(points[c.getX() - xstart][c.getY() - ystart]);
		}
	}

	public void taskEnded(TaskMoveAndMine t) {
		ended.add(t);
	}

	private void processEndedTask(TaskMoveAndMine t) {
		Coords3D left = t.getMiningTarget().getLeft();
		if (allContains(left) && PathFinder.shouldBeReachableSurrounding(left)) {
			toRelease.add(points[left.getX() - xstart][left.getY() - ystart]);
			all.remove(points[left.getX() - xstart][left.getY() - ystart]);
		}

		Coords3D right = t.getMiningTarget().getRight();
		if (allContains(right) && PathFinder.shouldBeReachableSurrounding(right)) {
			toRelease.add(points[right.getX() - xstart][right.getY() - ystart]);
			all.remove(points[right.getX() - xstart][right.getY() - ystart]);
		}

		Coords3D top = t.getMiningTarget().getTop();
		if (allContains(top) && PathFinder.shouldBeReachableSurrounding(top)) {
			toRelease.add(points[top.getX() - xstart][top.getY() - ystart]);
			all.remove(points[top.getX() - xstart][top.getY() - ystart]);
		}

		Coords3D bot = t.getMiningTarget().getBottom();
		if (allContains(bot) && PathFinder.shouldBeReachableSurrounding(bot)) {
			toRelease.add(points[bot.getX() - xstart][bot.getY() - ystart]);
			all.remove(points[bot.getX() - xstart][bot.getY() - ystart]);
		}
	}

	private boolean allContains(Coords3D bot) {
		for (TaskMoveAndMine a : all) {
			if (a.getMiningTarget().equals(bot))
				return true;
		}
		return false;
	}

	public boolean isCompleted() {
		return all.size() == 0 && toRelease.size() == 0 && ended.size() == 0 && sorted == true;
	}

	public ArrayList<TaskMoveAndMine> getNextTasks() {
		ArrayList<TaskMoveAndMine> returnList = new ArrayList<TaskMoveAndMine>();
		returnList.addAll(toRelease);
		toRelease.clear();
		return returnList;
	}

	private void sort() {
		// check all tasks for reachability
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (points[x][y] != null) {
					Coords3D c = points[x][y].getMiningTarget();
					if (PathFinder.shouldBeReachableSurrounding(c)) {
						toRelease.add(points[x][y]);
					}
				}
			}
		}
		sorted = true;
	}

	@Override
	public void run() {
		sort();
		while (!isCompleted()) {
			for (TaskMoveAndMine t : ended) {
				processEndedTask(t);
			}
			ended.clear();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
