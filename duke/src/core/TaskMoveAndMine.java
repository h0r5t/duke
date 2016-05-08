package core;

import java.util.ArrayList;

public class TaskMoveAndMine extends TaskChain {

	public TaskMoveAndMine(Coords3D targetToMine) {
		super(TaskType.MINE);
		TaskMove moveTask = new TaskMove(getPossibleTargets(targetToMine));
		queueTask(moveTask);

		TaskActionMine miningTask = new TaskActionMine(targetToMine);
		queueTask(miningTask);
	}

	private ArrayList<Coords3D> getPossibleTargets(Coords3D targetToMine) {
		int x = targetToMine.getX();
		int y = targetToMine.getY();

		ArrayList<Coords3D> possibleTargets = new ArrayList<Coords3D>();
		Tile t1 = Core.getWorld().getTile(x + 1, y, targetToMine.getZ());
		Tile t2 = Core.getWorld().getTile(x - 1, y, targetToMine.getZ());
		Tile t3 = Core.getWorld().getTile(x, y + 1, targetToMine.getZ());
		Tile t4 = Core.getWorld().getTile(x, y - 1, targetToMine.getZ());
		possibleTargets.add(t1.getCoords3D());
		possibleTargets.add(t2.getCoords3D());
		possibleTargets.add(t3.getCoords3D());
		possibleTargets.add(t4.getCoords3D());

		return possibleTargets;
	}

}
