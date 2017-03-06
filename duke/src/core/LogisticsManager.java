package core;

public class LogisticsManager {

	private Core core;
	private StockpileManager stockPileManager;
	private BuildingManager buildingManager;

	public LogisticsManager(Core core) {
		this.core = core;
		this.stockPileManager = new StockpileManager();
		this.buildingManager = new BuildingManager();
	}

	public StockpileManager getStockPileManager() {
		return stockPileManager;
	}

	public BuildingManager getBuildingManager() {
		return buildingManager;
	}

	public Stockpile getStockpileToGetItem(int itemID) {
		return stockPileManager.getStockpileWithItem(itemID);
	}

}
