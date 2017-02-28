package core;

import java.util.ArrayList;

public class TaskBuild extends TaskChain {

	public TaskBuild(Building building, LogisticsManager logisticsMgr) {
		super(TaskType.BUILD);

		for (ItemInput input : building.getItemInput()) {
			Stockpile s = logisticsMgr.getStockpileToGetItem(input.getItemID());
			ArrayList<Item> items = s.getItemsOfType(input.getItemID(), input.getAmount());
			for (Item item : items) {
				System.out.println(item);
				TaskMoveItem moveItem = new TaskMoveItem(item, building.getCoords3D());
				queueTask(moveItem);
			}

		}
	}

}
