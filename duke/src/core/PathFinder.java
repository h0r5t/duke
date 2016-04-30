package core;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;

import pathfinder.GraphNode;
import pathfinder.GraphSearch_Astar;

public class PathFinder {

	private Core core;
	private World world;

	public PathFinder(Core core) {
		this.core = core;
		this.world = Core.getWorld();
	}

	public TilePath findPath(Tile from, Tile to) {
		PrintStream originalStream = System.out;
		PrintStream dummyStream = null;
		try {
			dummyStream = new PrintStream("a");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(dummyStream);

		TilePath path = new TilePath(core);
		GraphSearch_Astar search = new GraphSearch_Astar(world);
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

}
