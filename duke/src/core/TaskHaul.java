package core;

public class TaskHaul extends TaskChain {

	private Item itemToHaul;

	public TaskHaul(Item i) {
		super(TaskType.HAULING);
		itemToHaul = i;
	}

	@Override
	public void onPickUp(Unit u) {
		Stockpile p = Core.getLogisticsManager().getStockpileManager().getStockpileForItem(itemToHaul.getClass());
		if (p == null || p.isFull()) {
			itemToHaul.setClaimed(false);
			setStatus(TaskStatus.DONE);
			return;
		}
		TaskMove move1 = new TaskMove(Core.getWorld().getItemPos(itemToHaul));

		TaskActionPickupItem pickUp = new TaskActionPickupItem(itemToHaul);

		Coords3D dropLocation = p.getNextFreeSlot();
		if (dropLocation == null) {
			itemToHaul.setClaimed(false);
			setStatus(TaskStatus.DONE);
			return;
		}
		p.markAsUsed(dropLocation);
		TaskMove move2 = new TaskMove(dropLocation);

		TaskActionDropItem drop = new TaskActionDropItem(itemToHaul);

		queueTask(move1);
		queueTask(pickUp);
		queueTask(move2);
		queueTask(drop);
	}

}
