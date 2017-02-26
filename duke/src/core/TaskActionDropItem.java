package core;

public class TaskActionDropItem extends TaskAction {

	private Item item;
	private Unit unit;

	public TaskActionDropItem(Item i) {
		super(TaskType.DROP_ITEM);
		item = i;
	}

	@Override
	public void callback(int context) {
		unit.removeItemFromHands();
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
