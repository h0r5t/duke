package core;

import java.util.ArrayList;

public class TaskBuild extends TaskChain {

	public TaskBuild(Building building, LogisticsManager logisticsMgr) {
		super(TaskType.BUILDING);

		ArrayList<Item> inputItems = new ArrayList<>();

		for (ItemInput input : building.getItemInputs()) {
			Stockpile s = logisticsMgr.getStockpileManager().getStockpileWithItem(input.getItemType());
			ArrayList<Item> items = s.getItemsOfType(input.getItemType(), input.getAmount());
			if (items == null) {
				setStatus(TaskStatus.FAILED);
				return;
			}
			for (Item item : items) {
				TaskMoveItem moveItem = new TaskMoveItem(item, building.getCoords3D());
				inputItems.add(item);
				queueTask(moveItem);
			}
		}

		queueTask(new TaskActionCustom(TaskType.BUSY, 3000) {

			@Override
			public void callback(Unit unit) {
				building.wasBuilt();
				for (Item i : inputItems) {
					i.destroy();
				}
			}
		});
	}

	@Override
	public void onPickUp(Unit u) {

	}

}
