package core;

public class TaskChopTree extends TaskChain {

	public TaskChopTree(Coords3D targetTrunk) {
		super(TaskType.TREE_CHOPPING);
		Zone2D dummyZone = new Zone2D(0, 0, 0, SelectionType.TYPE_DESIGNATION);
		targetTrunk.getTile().select(dummyZone);
		queueTask(new TaskMove(getPossibleTargets(targetTrunk)));
		queueTask(new TaskActionBusy(3000, new SimpleCallback() {

			@Override
			public void callback() {
				onTrunkDestroyed(targetTrunk);
			}

		}));
	}

	private void onTrunkDestroyed(Coords3D targetTrunk) {
		Core.getWorld().wasMined(targetTrunk);
	}

	@Override
	public void onPickUp(Unit u) {

	}

}
