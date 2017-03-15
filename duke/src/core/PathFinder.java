package core;

import java.util.ArrayList;
import java.util.LinkedList;

import pathfinder.GraphNode;
import pathfinder.GraphSearch_Astar;

public class PathFinder {

	private static Coords3D reachablePoint;

	public static void setReachablePoint(Coords3D point) {
		reachablePoint = point;
	}

	public static boolean shouldBeReachableSurrounding(Coords3D point) {
		return pathExists(reachablePoint, point) || pathExists(reachablePoint, point.getRight())
				|| pathExists(reachablePoint, point.getLeft()) || pathExists(reachablePoint, point.getTop())
				|| pathExists(reachablePoint, point.getBottom());
	}

	public static double estimateDistance2D(Coords3D source, Coords3D target) {
		int x = Math.abs(source.getX() - target.getX());
		int y = Math.abs(source.getY() - target.getY());
		return x + y;
	}

	public static int estimateTimeNeeded(Unit u, Coords3D target) {
		int distance = (int) estimateDistance2D(u.getCoords(), target);
		return (int) (distance * u.getMoveSpeed() * Settings.TICK_TIME);
	}

	public static boolean shouldBeReachable(Coords3D point) {
		return pathExists(reachablePoint, point);
	}

	public static boolean pathExists(Coords3D from, Coords3D to) {
		Path t = findPath(from, to);
		return t.isPossible();
	}

	public static Coords3D findTargetTileWithShortestPath(Coords3D from, ArrayList<Coords3D> to) {
		if (to.size() == 1)
			return to.get(0);
		Tile currentShortest = null;
		int length = Integer.MAX_VALUE;

		for (int i = 0; i < to.size(); i++) {
			Path t = findPath(from, to.get(i));
			if (!t.isPossible())
				continue;
			if (t.getPathLength() < length) {
				currentShortest = to.get(i).getTile();
				length = t.getPathLength();
			}

		}
		if (currentShortest == null)
			return null;
		return currentShortest.getCoords3D();
	}

	public static Path findPath(Coords3D from, Coords3D to) {
		if (from == null || to == null)
			return null;

		Path path = new Path(true);
		GraphSearch_Astar search = new GraphSearch_Astar(Core.getWorld());
		LinkedList<GraphNode> route = search.search(from.getTile().id(), to.getTile().id());
		if (route == null) {
			return new Path(false);
		}
		for (GraphNode node : route) {
			path.add(new Coords3D((int) node.x(), (int) node.y(), (int) node.z()));
		}
		return path;
	}
}
