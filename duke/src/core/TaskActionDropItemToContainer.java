package core;

public class TaskActionDropItemToContainer extends TaskAction {

	private ItemContainer container;

	public TaskActionDropItemToContainer(ItemContainer container) {
		super(TaskType.DROPPING_ITEM, 1000);
		this.container = container;
	}

	@Override
	public void callback(Unit unit) {
		Item item = unit.removeItemFromHands();
		item.moveTo(container.getCoords3D());
		item.setVisible(false);
		container.addItem(item);
	}

	@Override
	protected void doAction(Unit unit) {
		startTimer();
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}

}
