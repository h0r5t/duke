package core;

public class TaskHaul extends TaskChain {

	private Item itemToHaul;

	public TaskHaul(Item i) {
		super(TaskType.HAULING);
		itemToHaul = i;
	}

	@Override
	public void onPickUp(Unit u) {
		// try to put into container first
		ItemContainer container = Core.getLogisticsManager().getContainerForItem(itemToHaul);
		if (container != null) {
			container.reserveSpaceFor(itemToHaul);

			TaskMove move1 = new TaskMove(Core.getWorld().getItemPos(itemToHaul));
			TaskActionPickupItem pickUp = new TaskActionPickupItem(itemToHaul);
			TaskMove move2 = new TaskMove(getReachableSurroundingTiles(container.getCoords3D()));
			TaskActionDropItemToContainer dropToCointainer = new TaskActionDropItemToContainer(container);

			queueTask(move1);
			queueTask(pickUp);
			queueTask(move2);
			queueTask(dropToCointainer);
		}

		// if no container, put on a stockpile
		else {
			Stockpile p = Core.getLogisticsManager().getStockpileManager().getStockpileForItem(itemToHaul.getClass());
			bringToStockpile(p);

			// no space anywhere, item cannot be hauled
			if (p == null || p.isFull()) {
				itemToHaul.setClaimed(false);
				setStatus(TaskStatus.DONE);
				return;
			}
		}

	}

	private void bringToStockpile(Stockpile p) {
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
