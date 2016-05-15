package core;

public class TaskHaul extends TaskChain {

	private Item itemToHaul;
	private Stockpile stockpile;

	public TaskHaul(Item i, Stockpile stockpile) {
		super(TaskType.HAUL);
		itemToHaul = i;
		this.stockpile = stockpile;

		TaskMove move1 = new TaskMove(Core.getWorld().getItemPos(itemToHaul));
		queueTask(move1);

		TaskActionPickupItem pickUp = new TaskActionPickupItem(itemToHaul);
		queueTask(pickUp);

		TaskMove move2 = new TaskMove(stockpile.getZone().getCoords());
		queueTask(move2);

		TaskActionDropItem drop = new TaskActionDropItem(itemToHaul);
		queueTask(drop);
	}

}
