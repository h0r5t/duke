package core;

public class TaskActionPickupItem extends TaskAction {

	private Item item;
	private Unit unit;

	public TaskActionPickupItem(Item i) {
		super(TaskType.PICKING_UP_ITEM, 250);
		item = i;
	}

	@Override
	public void callback(int context) {
		unit.setItemInHands(item);
		setStatus(TaskStatus.DONE);
	}

	@Override
	protected void doAction(Unit unit) {
		this.unit = unit;
		startTimer();
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}

}
