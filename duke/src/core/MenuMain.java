package core;

public class MenuMain extends Menu {

	public MenuMain(MenuManager menuManager) {
		super(menuManager);
		addItem(new MenuItem("designations", "r", new MenuDesignations(menuManager)));
		addItem(new MenuItem("build", "b", new MenuBuild(menuManager)));
		addItem(new MenuItem("zones", "z", new MenuZones(menuManager)));
		addItem(new MenuItem("claim", "c", new MenuClaim(menuManager)));
	}

	@Override
	public String getName() {
		return "main";
	}

	@Override
	public void start() {

	}

	@Override
	protected void itemWasClicked(MenuItem i) {
	}

}
