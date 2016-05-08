package core;

import java.util.ArrayList;

public class TaskMoveAndMine extends TaskChain {

	private Coords3D miningTarget;
	private TaskGroupMining myTaskGroup;

	public TaskMoveAndMine(Coords3D targetToMine) {
		super(TaskType.MINE);
		this.miningTarget = targetToMine;
		TaskMove moveTask = new TaskMove(getPossibleTargets(targetToMine));
		queueTask(moveTask);

		TaskActionMine miningTask = new TaskActionMine(targetToMine);
		queueTask(miningTask);
	}

	public void setTaskGroup(TaskGroupMining t) {
		this.myTaskGroup = t;
	}

	public TaskGroupMining getTaskGroup() {
		return myTaskGroup;
	}

	public Coords3D getMiningTarget() {
		return miningTarget;
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

	@Override
	public void update(Unit unit) {
		if (pickedUp == false) {
			pickedUp = true;
			unit.setCurrentTask(taskChain.get(0));
			unit.setActiveTaskChain(this);
		}
		if (taskChain.get(0).getStatus() == TaskStatus.DONE) {
			taskChain.remove(0);
			if (taskChain.size() == 0) {
				setStatus(TaskStatus.DONE);
				unit.setActiveTaskChain(null);
				TaskGroupMining taskGroup = getTaskGroup();
				if (taskGroup != null) {
					taskGroup.removeLock();
				}
			} else {
				unit.setCurrentTask(taskChain.get(0));
			}
		}
	}

}
