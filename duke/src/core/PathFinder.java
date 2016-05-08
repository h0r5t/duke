package core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import pathfinder.GraphNode;
import pathfinder.GraphSearch_Astar;

public class PathFinder {

	public static boolean pathExists(Coords3D from, Coords3D to) {
		Path t = findPath(from, to);
		return t != null;
	}

	public static Coords3D findTargetTileWithShortestPath(Coords3D from,
			ArrayList<Coords3D> to) {
		Tile currentShortest = null;
		int length = Integer.MAX_VALUE;

		for (int i = 0; i < to.size(); i++) {
			Path t = findPath(from, to.get(i));
			if (t == null)
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

	// public static Tile findSourceTileWithShortestPath(ArrayList<Tile> from,
	// Tile to) {
	// Tile currentShortest = from.get(0);
	// int length = Integer.MAX_VALUE;
	//
	// for (int i = 0; i < from.size(); i++) {
	// Path t = findPath(from.get(i), to);
	// if (t == null)
	// continue;
	// if (t.getPathLength() < length) {
	// currentShortest = from.get(i);
	// length = t.getPathLength();
	// }
	//
	// }
	// return currentShortest;
	// }

	// public static Unit findUnitWithShortestPath(ArrayList<Unit> units,
	// Tile to) {
	// Unit currentShortest = units.get(0);
	// int length = Integer.MAX_VALUE;
	//
	// for (int i = 0; i < units.size(); i++) {
	// Path t = findPath(units.get(i).getTile(), to);
	// if (t == null) {
	// continue;
	// }
	//
	// if (t.getPathLength() < length) {
	// currentShortest = units.get(i);
	// length = t.getPathLength();
	// }
	//
	// }
	// return currentShortest;
	// }

	public static Path findPath(Coords3D from, Coords3D to) {
		if (from == null || to == null)
			return null;
		PrintStream originalStream = System.out;
		PrintStream dummyStream = null;
		dummyStream = new PrintStream(new NullOutputStream());
		System.setOut(dummyStream);

		Path path = new Path();
		GraphSearch_Astar search = new GraphSearch_Astar(Core.getWorld());
		LinkedList<GraphNode> route = search.search(from.getTile().id(),
				to.getTile().id());
		if (route == null) {
			System.setOut(originalStream);
			return null;
		}
		for (GraphNode node : route) {
			Tile t = (Tile) node;
			path.add(t.getCoords3D());
		}

		System.setOut(originalStream);
		return path;
	}

	private static class NullOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
		}
	}

}
