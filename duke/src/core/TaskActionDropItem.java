package core;

public class TaskActionDropItem extends TaskAction {

	private Item item;
	private Unit unit;

	public TaskActionDropItem(Item i) {
		super(TaskType.DROPPING_ITEM, 250);
		item = i;
	}

	@Override
	public void callback(Unit unit) {
		unit.removeItemFromHands();
		StockpileManager stockM = Core.getLogisticsManager().getStockpileManager();
		Stockpile s = stockM.getStockpileForPos(unit.getCoords());
		if (s != null) {
			s.addItem(item);
		}
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
