package core;

import java.util.ArrayList;

public class TaskGroupMining {

	private TaskMoveAndMine[][] points;
	private ArrayList<TaskMoveAndMine> all;
	private ArrayList<TaskMoveAndMine> toRelease;
	private int xstart;
	private int ystart;
	private int width;
	private int height;

	public TaskGroupMining(AreaSelection area) {
		this.xstart = area.getX();
		this.ystart = area.getY();
		this.width = area.getWidth() + 1;
		this.height = area.getHeight() + 1;
		points = new TaskMoveAndMine[this.width][this.height];
		all = new ArrayList<TaskMoveAndMine>();
		toRelease = new ArrayList<TaskMoveAndMine>();

		for (Coords3D c : area.getLocations()) {
			if (!c.getTile().collides()) {
				c.getTile().deselect(area);
				continue;
			}
			points[c.getX() - xstart][c.getY() - ystart] = new TaskMoveAndMine(c);
			points[c.getX() - xstart][c.getY() - ystart].setTaskGroup(this);
			all.add(points[c.getX() - xstart][c.getY() - ystart]);
		}
		sort();
	}

	public void taskEnded(TaskMoveAndMine t) {
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

	public ArrayList<TaskMoveAndMine> getNextTasks() {
		ArrayList<TaskMoveAndMine> returnList = new ArrayList<TaskMoveAndMine>();
		for (TaskMoveAndMine t : toRelease) {
			returnList.add(t);
		}
		toRelease.clear();
		return returnList;
	}

	private void sort() {
		// check all outer tasks for reachability
		for (int x = 0; x < width; x++) {
			Coords3D c1 = points[x][0].getMiningTarget();
			Coords3D c2 = points[x][height - 1].getMiningTarget();
			if (PathFinder.shouldBeReachableSurrounding(c1)) {
				toRelease.add(points[x][0]);
			}
			if (PathFinder.shouldBeReachableSurrounding(c2)) {
				toRelease.add(points[x][height - 1]);
			}
		}

		for (int y = 0; y < height; y++) {
			Coords3D c1 = points[0][y].getMiningTarget();
			Coords3D c2 = points[width - 1][y].getMiningTarget();
			if (PathFinder.shouldBeReachableSurrounding(c1)) {
				toRelease.add(points[0][y]);
			}
			if (PathFinder.shouldBeReachableSurrounding(c2)) {
				toRelease.add(points[width - 1][y]);
			}
		}
	}

	// public void exploreFrom(int xReachable, int yReachable) {
	//
	// if (yReachable == 0) {
	// int dividerX = xReachable;
	// for (int y = 0; y < height; y++) {
	// for (int x = dividerX; x >= 0; x--) {
	// sortedList.add(points[x][y]);
	// }
	// for (int x = dividerX + 1; x < width; x++) {
	// sortedList.add(points[x][y]);
	// }
	// }
	// }
	//
	// else if (yReachable == height - 1) {
	// int dividerX = xReachable;
	// for (int y = height - 1; y >= 0; y--) {
	// for (int x = dividerX; x >= 0; x--) {
	// sortedList.add(points[x][y]);
	// }
	// for (int x = dividerX + 1; x < width; x++) {
	// sortedList.add(points[x][y]);
	// }
	// }
	// }
	//
	// else if (xReachable == 0) {
	// int dividerY = yReachable;
	// for (int x = 0; x < width; x++) {
	// for (int y = dividerY; y >= 0; y--) {
	// sortedList.add(points[x][y]);
	// }
	// for (int y = dividerY + 1; y < height; y++) {
	// sortedList.add(points[x][y]);
	// }
	// }
	// }
	//
	// else if (xReachable == width - 1) {
	// int dividerY = yReachable;
	// for (int x = width - 1; x >= 0; x--) {
	// if (x == width - 1) {
	// for (int y = dividerY; y >= 0; y--) {
	// sortedList.add(points[x][y]);
	// }
	// for (int y = dividerY + 1; y < height; y++) {
	// sortedList.add(points[x][y]);
	// }
	// }
	// }
	// }
	//
	// ArrayList<TaskMoveAndMine> toDel = new ArrayList<TaskMoveAndMine>();
	// for (TaskMoveAndMine a : sortedList) {
	// if (!a.getMiningTarget().getTile().collides()) {
	// toDel.add(a);
	// }
	// }
	// sortedList.removeAll(toDel);
	//
	// }

}
