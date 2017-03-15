package core;

public class TaskHaul extends TaskChain {

	private Item itemToHaul;

	public TaskHaul(Item i, Stockpile stockpile) {
		super(TaskType.HAULING);
		itemToHaul = i;

		TaskMove move1 = new TaskMove(Core.getWorld().getItemPos(itemToHaul));

		TaskActionPickupItem pickUp = new TaskActionPickupItem(itemToHaul);

		Coords3D dropLocation = stockpile.getNextFreeSlot();
		if (dropLocation == null) {
			setStatus(TaskStatus.DONE);
			return;
		}
		stockpile.markAsUsed(dropLocation);
		TaskMove move2 = new TaskMove(dropLocation);

		TaskActionDropItem drop = new TaskActionDropItem(itemToHaul);

		queueTask(move1);
		queueTask(pickUp);
		queueTask(move2);
		queueTask(drop);
	}

}
