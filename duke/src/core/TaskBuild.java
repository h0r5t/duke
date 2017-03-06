package core;

import java.util.ArrayList;

public class TaskBuild extends TaskChain {

	public TaskBuild(Building building, LogisticsManager logisticsMgr) {
		super(TaskType.BUILD);

		ArrayList<Item> inputItems = new ArrayList<>();

		for (ItemInput input : building.getItemInput()) {
			Stockpile s = logisticsMgr.getStockpileToGetItem(input.getItemID());
			ArrayList<Item> items = s.getItemsOfType(input.getItemID(), input.getAmount());
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

}
