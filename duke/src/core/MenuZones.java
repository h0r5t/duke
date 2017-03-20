package core;

public class MenuZones extends Menu {

	public MenuZones(MenuManager menuManager) {
		super(menuManager);
		addItem(new MenuItem("stockpiles", "s", new MenuStockpiles(menuManager)));
	}

	@Override
	protected void itemWasClicked(MenuItem i) {

	}

	@Override
	public String getName() {
		return "zones";
	}

	@Override
	public void start() {

	}

}
