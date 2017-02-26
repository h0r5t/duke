package core;

public class TaskActionPickupItem extends TaskAction {

	private Item item;
	private Unit unit;

	public TaskActionPickupItem(Item i) {
		super(TaskType.PICK_UP_ITEM);
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
		startTimer(250, 0);
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}

}
