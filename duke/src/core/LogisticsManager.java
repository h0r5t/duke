package core;

import java.util.ArrayList;

public class LogisticsManager {

	private StockpileManager stockPileManager;
	private BuildingManager buildingManager;
	private ArrayList<ItemContainer> itemContainers;

	public LogisticsManager() {
		this.stockPileManager = new StockpileManager();
		this.buildingManager = new BuildingManager();
		this.itemContainers = new ArrayList<>();
	}

	public ItemContainer getContainerForItem(Item i) {
		for (ItemContainer c : itemContainers) {
			if (c.canAddItem(i)) {
				return c;
			}
		}

		return null;
	}

	public void addItemContainer(ItemContainer ic) {
		itemContainers.add(ic);
	}

	public void removeItemContainer(ItemContainer ic) {
		itemContainers.remove(ic);
	}

	public StockpileManager getStockpileManager() {
		return stockPileManager;
	}

	public BuildingManager getBuildingManager() {
		return buildingManager;
	}

}
