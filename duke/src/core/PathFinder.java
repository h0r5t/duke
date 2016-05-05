package core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

import pathfinder.GraphNode;
import pathfinder.GraphSearch_Astar;

public class PathFinder {

	public PathFinder() {
	}

	public static boolean pathExists(Tile from, Tile to) {
		TilePath t = findPath(from, to);
		return t != null;
	}

	public static Tile findTargetTileWithShortestPath(Tile from, ArrayList<Tile> to) {
		Tile currentShortest = to.get(0);
		int length = Integer.MAX_VALUE;

		for (int i = 0; i < to.size(); i++) {
			TilePath t = findPath(from, to.get(i));
			if (t == null)
				continue;
			if (t.getPathLength() < length) {
				currentShortest = to.get(i);
				length = t.getPathLength();
			}

		}
		return currentShortest;
	}

	public static Tile findSourceTileWithShortestPath(ArrayList<Tile> from, Tile to) {
		Tile currentShortest = from.get(0);
		int length = Integer.MAX_VALUE;

		for (int i = 0; i < from.size(); i++) {
			TilePath t = findPath(from.get(i), to);
			if (t == null)
				continue;
			if (t.getPathLength() < length) {
				currentShortest = from.get(i);
				length = t.getPathLength();
			}

		}
		return currentShortest;
	}

	public static Unit findUnitWithShortestPath(ArrayList<Unit> units, Tile to) {
		Unit currentShortest = units.get(0);
		int length = Integer.MAX_VALUE;

		for (int i = 0; i < units.size(); i++) {
			TilePath t = findPath(units.get(i).getTile(), to);
			if (t == null) {
				continue;
			}

			if (t.getPathLength() < length) {
				currentShortest = units.get(i);
				length = t.getPathLength();
			}

		}
		return currentShortest;
	}

	public static TilePath findPath(Tile from, Tile to) {
		if (from == null || to == null)
			return null;
		PrintStream originalStream = System.out;
		PrintStream dummyStream = null;
		dummyStream = new PrintStream(new NullOutputStream());
		System.setOut(dummyStream);

		TilePath path = new TilePath();
		GraphSearch_Astar search = new GraphSearch_Astar(Core.getWorld());
		LinkedList<GraphNode> route = search.search(from.id(), to.id());
		if (route == null) {
			System.setOut(originalStream);
			return null;
		}
		for (GraphNode node : route) {
			Tile t = (Tile) node;
			path.add(t);
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
