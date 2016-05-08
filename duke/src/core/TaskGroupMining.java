package core;

import java.util.ArrayList;

public class TaskGroupMining {

	private TaskMoveAndMine[][] points;
	private ArrayList<ArrayList<TaskMoveAndMine>> sortedList;
	private int width;
	private int height;
	private int lockCounter = 0;

	public TaskGroupMining(int xstart, int ystart, int width, int height,
			ArrayList<Coords3D> miningTargets) {
		this.width = width + 1;
		this.height = height + 1;
		points = new TaskMoveAndMine[this.width][this.height];
		sortedList = new ArrayList<ArrayList<TaskMoveAndMine>>();

		for (Coords3D c : miningTargets) {
			points[c.getX() - xstart][c.getY() - ystart] = new TaskMoveAndMine(
					c);
			points[c.getX() - xstart][c.getY() - ystart].setTaskGroup(this);
		}
		sort();
	}

	public void addLock() {
		lockCounter++;
	}

	public void removeLock() {
		lockCounter--;
	}

	public boolean isLocked() {
		return lockCounter != 0;
	}

	private void sort() {
		// check all outer tasks for reachability
		for (int x = 0; x < width; x++) {
			Coords3D c1 = points[x][0].getMiningTarget();
			Coords3D c2 = points[x][height - 1].getMiningTarget();
			if (PathFinder.shouldBeReachableRange1(c1)) {
				exploreFrom(x, 0);
				return;
			}
			if (PathFinder.shouldBeReachableRange1(c2)) {
				exploreFrom(x, height - 1);
				return;
			}
		}

		for (int y = 0; y < height; y++) {
			Coords3D c1 = points[0][y].getMiningTarget();
			Coords3D c2 = points[width - 1][y].getMiningTarget();
			if (PathFinder.shouldBeReachableRange1(c1)) {
				exploreFrom(0, y);
				return;
			}
			if (PathFinder.shouldBeReachableRange1(c2)) {
				exploreFrom(width - 1, y);
				return;
			}
		}
	}

	public void exploreFrom(int xReachable, int yReachable) {

		if (yReachable == 0) {
			int dividerX = xReachable;
			for (int y = 0; y < height; y++) {
				if (y == 0) {
					// one by one
					for (int x = dividerX; x >= 0; x--) {
						sortedList.add(newListWithItem(points[x][y]));
					}
					for (int x = dividerX + 1; x < width; x++) {
						sortedList.add(newListWithItem(points[x][y]));
					}
				} else {
					// add whole layer
					ArrayList<TaskMoveAndMine> layer = new ArrayList<TaskMoveAndMine>();
					for (int x = dividerX; x >= 0; x--) {
						layer.add(points[x][y]);
					}
					for (int x = dividerX + 1; x < width; x++) {
						layer.add(points[x][y]);
					}
					sortedList.add(layer);
				}
			}
		}

		else if (yReachable == height - 1) {
			int dividerX = xReachable;
			for (int y = height - 1; y >= 0; y--) {
				if (y == height - 1) {
					// one by one
					for (int x = dividerX; x >= 0; x--) {
						sortedList.add(newListWithItem(points[x][y]));
					}
					for (int x = dividerX + 1; x < width; x++) {
						sortedList.add(newListWithItem(points[x][y]));
					}
				} else {
					// add whole layer
					ArrayList<TaskMoveAndMine> layer = new ArrayList<TaskMoveAndMine>();
					for (int x = dividerX; x >= 0; x--) {
						layer.add(points[x][y]);
					}
					for (int x = dividerX + 1; x < width; x++) {
						layer.add(points[x][y]);
					}
					sortedList.add(layer);
				}
			}
		}

		else if (xReachable == 0) {
			int dividerY = yReachable;
			for (int x = 0; x < width; x++) {
				if (x == 0) {
					// one by one
					for (int y = dividerY; y >= 0; y--) {
						sortedList.add(newListWithItem(points[x][y]));
					}
					for (int y = dividerY + 1; y < height; y++) {
						sortedList.add(newListWithItem(points[x][y]));
					}
				} else {
					// add whole layer
					ArrayList<TaskMoveAndMine> layer = new ArrayList<TaskMoveAndMine>();
					for (int y = dividerY; y >= 0; y--) {
						layer.add(points[x][y]);
					}
					for (int y = dividerY + 1; y < height; y++) {
						layer.add(points[x][y]);
					}
					sortedList.add(layer);
				}
			}
		}

		else if (xReachable == width - 1) {
			int dividerY = yReachable;
			for (int x = width - 1; x >= 0; x--) {
				if (x == width - 1) {
					// one by one
					for (int y = dividerY; y >= 0; y--) {
						sortedList.add(newListWithItem(points[x][y]));
					}
					for (int y = dividerY + 1; y < height; y++) {
						sortedList.add(newListWithItem(points[x][y]));
					}
				} else {
					// add whole layer
					ArrayList<TaskMoveAndMine> layer = new ArrayList<TaskMoveAndMine>();
					for (int y = dividerY; y >= 0; y--) {
						layer.add(points[x][y]);
					}
					for (int y = dividerY + 1; y < height; y++) {
						layer.add(points[x][y]);
					}
					sortedList.add(layer);
				}
			}
		}

		ArrayList<TaskMoveAndMine> toDel = new ArrayList<TaskMoveAndMine>();
		for (ArrayList<TaskMoveAndMine> a : sortedList) {
			for (TaskMoveAndMine t : a) {
				if (!t.getMiningTarget().getTile().collides()) {
					toDel.add(t);
				}
			}
			for (TaskMoveAndMine t : toDel) {
				a.remove(t);
			}
			toDel.clear();
		}
		ArrayList<ArrayList<TaskMoveAndMine>> toDel2 = new ArrayList<ArrayList<TaskMoveAndMine>>();
		for (ArrayList<TaskMoveAndMine> a : sortedList) {
			if (a.size() == 0)
				toDel2.add(a);
		}
		sortedList.removeAll(toDel2);
	}

	private ArrayList<TaskMoveAndMine> newListWithItem(TaskMoveAndMine t) {
		ArrayList<TaskMoveAndMine> list = new ArrayList<TaskMoveAndMine>();
		list.add(t);
		return list;
	}

	public ArrayList<TaskMoveAndMine> getNextTaskLayer() {
		if (sortedList.size() == 0)
			return null;
		ArrayList<TaskMoveAndMine> nextLayer = sortedList.remove(0);
		return nextLayer;
	}

}
