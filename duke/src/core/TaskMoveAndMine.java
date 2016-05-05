package core;

import java.util.ArrayList;

public class TaskMoveAndMine extends TaskChain {

	public TaskMoveAndMine(Tile tileToMine) {
		TaskMoveMultipleTargets moveTask = new TaskMoveMultipleTargets(getPossibleTargets(tileToMine));
		queueTask(moveTask);

		TaskActionMine miningTask = new TaskActionMine(tileToMine);
		queueTask(miningTask);
	}

	private ArrayList<Tile> getPossibleTargets(Tile tileToMine) {
		int x = tileToMine.getX();
		int y = tileToMine.getY();

		ArrayList<Tile> possibleTargets = new ArrayList<Tile>();
		Tile t1 = Core.getWorld().getTile(x + 1, y, tileToMine.getZ());
		Tile t2 = Core.getWorld().getTile(x - 1, y, tileToMine.getZ());
		Tile t3 = Core.getWorld().getTile(x, y + 1, tileToMine.getZ());
		Tile t4 = Core.getWorld().getTile(x, y - 1, tileToMine.getZ());
		possibleTargets.add(t1);
		possibleTargets.add(t2);
		possibleTargets.add(t3);
		possibleTargets.add(t4);

		return possibleTargets;
	}

}
