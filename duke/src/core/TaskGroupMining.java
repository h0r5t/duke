package core;

import java.util.ArrayList;

public class TaskGroupMining {

	private TaskMoveAndMine[][] points;
	private ArrayList<TaskMoveAndMine> sortedList;
	private int width;
	private int height;

	public TaskGroupMining(AreaSelection area) {
		int xstart = area.getX();
		int ystart = area.getY();
		this.width = area.getWidth() + 1;
		this.height = area.getHeight() + 1;
		points = new TaskMoveAndMine[this.width][this.height];
		sortedList = new ArrayList<TaskMoveAndMine>();

		for (Coords3D c : area.getLocations()) {
			points[c.getX() - xstart][c.getY() - ystart] = new TaskMoveAndMine(c);
			points[c.getX() - xstart][c.getY() - ystart].setTaskGroup(this);
			if (!c.getTile().collides()) {
				c.getTile().deselect(area);
			}
		}
		sort();
	}

	private void sort() {
		// check all outer tasks for reachability
		for (int x = 0; x < width; x++) {
			Coords3D c1 = points[x][0].getMiningTarget();
			Coords3D c2 = points[x][height - 1].getMiningTarget();
			if (PathFinder.shouldBeReachableSurrounding(c1)) {
				exploreFrom(x, 0);
				return;
			}
			if (PathFinder.shouldBeReachableSurrounding(c2)) {
				exploreFrom(x, height - 1);
				return;
			}
		}

		for (int y = 0; y < height; y++) {
			Coords3D c1 = points[0][y].getMiningTarget();
			Coords3D c2 = points[width - 1][y].getMiningTarget();
			if (PathFinder.shouldBeReachableSurrounding(c1)) {
				exploreFrom(0, y);
				return;
			}
			if (PathFinder.shouldBeReachableSurrounding(c2)) {
				exploreFrom(width - 1, y);
				return;
			}
		}
	}

	public void exploreFrom(int xReachable, int yReachable) {

		if (yReachable == 0) {
			int dividerX = xReachable;
			for (int y = 0; y < height; y++) {
				for (int x = dividerX; x >= 0; x--) {
					sortedList.add(points[x][y]);
				}
				for (int x = dividerX + 1; x < width; x++) {
					sortedList.add(points[x][y]);
				}
			}
		}

		else if (yReachable == height - 1) {
			int dividerX = xReachable;
			for (int y = height - 1; y >= 0; y--) {
				for (int x = dividerX; x >= 0; x--) {
					sortedList.add(points[x][y]);
				}
				for (int x = dividerX + 1; x < width; x++) {
					sortedList.add(points[x][y]);
				}
			}
		}

		else if (xReachable == 0) {
			int dividerY = yReachable;
			for (int x = 0; x < width; x++) {
				for (int y = dividerY; y >= 0; y--) {
					sortedList.add(points[x][y]);
				}
				for (int y = dividerY + 1; y < height; y++) {
					sortedList.add(points[x][y]);
				}
			}
		}

		else if (xReachable == width - 1) {
			int dividerY = yReachable;
			for (int x = width - 1; x >= 0; x--) {
				if (x == width - 1) {
					for (int y = dividerY; y >= 0; y--) {
						sortedList.add(points[x][y]);
					}
					for (int y = dividerY + 1; y < height; y++) {
						sortedList.add(points[x][y]);
					}
				}
			}
		}

		ArrayList<TaskMoveAndMine> toDel = new ArrayList<TaskMoveAndMine>();
		for (TaskMoveAndMine a : sortedList) {
			if (!a.getMiningTarget().getTile().collides()) {
				toDel.add(a);
			}
		}
		sortedList.removeAll(toDel);

	}

	public TaskMoveAndMine getNextTask() {
		if (sortedList.size() == 0)
			return null;
		TaskMoveAndMine next = sortedList.get(0);
		if (PathFinder.shouldBeReachableSurrounding(next.getMiningTarget())) {
			sortedList.remove(0);
			return next;
		}
		return null;
	}

}
