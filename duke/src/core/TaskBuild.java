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

		queueTask(new TaskActionBusy(3000, new SimpleCallback() {

			@Override
			public void callback() {
				building.wasBuilt();
				for (Item i : inputItems) {
					i.destroy();
				}
			}
		}));
	}

	@Override
	public void onPickUp(Unit u) {

	}

}
