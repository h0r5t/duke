package core;

public class LogisticsManager {

	private StockpileManager stockPileManager;
	private BuildingManager buildingManager;

	public LogisticsManager() {
		this.stockPileManager = new StockpileManager();
		this.buildingManager = new BuildingManager();
	}

	public StockpileManager getStockpileManager() {
		return stockPileManager;
	}

	public BuildingManager getBuildingManager() {
		return buildingManager;
	}

}
