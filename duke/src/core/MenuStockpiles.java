package core;

public class MenuStockpiles extends Menu {

	public MenuStockpiles(MenuManager menuManager) {
		super(menuManager);
		addFooterItem(new MenuItem("create", "e"));
		addFooterItem(new MenuItem("remove", "r"));
	}

	@Override
	protected void itemWasClicked(MenuItem i) {
		if (i.getHotkey().equals("e")) {
			Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_ZONE));
			if (zone == null)
				return;
			menuManager.getCore().getLogisticsManager().getStockpileManager().addStockpile(new Stockpile(zone));
			zone.reset();

		} else if (i.getHotkey().equals("r")) {
			Coords3D c = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
			if (c == null)
				return;
			Stockpile sp = menuManager.getCore().getLogisticsManager().getStockpileManager().getStockpileForPos(c);
			if (sp != null)
				menuManager.getCore().getLogisticsManager().getStockpileManager().removeStockpile(sp);
		}
	}

	@Override
	public String getName() {
		return "stockpiles";
	}

	@Override
	public void start() {

	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCursorMoved(Cursor cursor) {
		// TODO Auto-generated method stub

	}

}
