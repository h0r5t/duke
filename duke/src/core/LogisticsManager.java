package core;

public class LogisticsManager {

	private Core core;
	private StockpileManager stockPileManager;

	public LogisticsManager(Core core) {
		this.core = core;
		this.stockPileManager = new StockpileManager();
	}

	public StockpileManager getStockPileManager() {
		return stockPileManager;
	}

	public Stockpile getStockpileToGetItem(int itemID) {
		return stockPileManager.getStockpileWithItem(itemID);
	}

}
