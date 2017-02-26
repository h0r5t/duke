package core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuZones extends Menu {

	public MenuZones(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "zones";
	}

	@Override
	public void start() {
		menuManager.getCore().getLogisticsManager().getStockPileManager().setStockpileMarkers(true);
		selectionMenu();
		menuManager.getCore().getLogisticsManager().getStockPileManager().setStockpileMarkers(false);
	}

	private void selectionMenu() {
		String[] options = new String[] { "stockpile", "test" };
		String resultString = (String) getSelectionResult(new SelectorToolTipOneLayer(menuManager, options));
		if (resultString == null)
			return;

		if (resultString.equals("stockpile"))
			manageStockpiles();

	}

	private void manageStockpiles() {
		ArrayList<SelectorToolTipQuery> queries = new ArrayList<>();
		queries.add(new SelectorToolTipQuery("stockpile", new String[] { "create", "remove" }));

		@SuppressWarnings("unchecked")
		HashMap<String, String> resultsMap = (HashMap<String, String>) getSelectionResult(
				new SelectorToolTipTwoLayers(menuManager, queries));
		if (resultsMap == null) {
			return;
		}

		if (resultsMap.get("stockpile").equals("create")) {
			Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_ZONE));
			if (zone == null)
				return;
			zone.reset();
			Stockpile s = new Stockpile(zone);
			menuManager.getCore().getLogisticsManager().getStockPileManager().addStockpile(s);
		}

		else if (resultsMap.get("stockpile").equals("remove")) {
			Coords3D target = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
			if (target == null)
				return;
			Stockpile s = menuManager.getCore().getLogisticsManager().getStockPileManager().getStockpileForPos(target);
			if (s != null)
				menuManager.getCore().getLogisticsManager().getStockPileManager().removeStockpile(s);
		}

		selectionMenu();
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
