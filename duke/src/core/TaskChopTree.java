package core;

public class TaskChopTree extends TaskChain {

	public TaskChopTree(Coords3D targetTrunk) {
		super(TaskType.TREE_CHOPPING);
		Zone2D dummyZone = new Zone2D(0, 0, 0, SelectionType.TYPE_DESIGNATION);
		targetTrunk.getTile().select(dummyZone);
		queueTask(new TaskMove(getReachableSurroundingTiles(targetTrunk)));
		queueTask(new TaskActionCustom(TaskType.BUSY, 3000) {

			@Override
			public void callback(Unit unit) {
				onTrunkDestroyed(targetTrunk);
			}
		});
	}

	private void onTrunkDestroyed(Coords3D targetTrunk) {
		Core.getWorld().wasMined(targetTrunk);
	}

	@Override
	public void onPickUp(Unit u) {

	}

}
