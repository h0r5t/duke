package core;

public class MenuMain extends Menu {

	public MenuMain(MenuManager menuManager) {
		super(menuManager);
		addItem(new MenuItem("designations", "r", new MenuDesignations(menuManager)));
		addItem(new MenuItem("build", "b", new MenuBuild(menuManager)));
		addItem(new MenuItem("zones", "z", new MenuZones(menuManager)));
		addItem(new MenuItem("claim", "c", new MenuClaim(menuManager)));
		addItem(new MenuItem("test", "t", new MenuTest(menuManager)));
		addItem(new MenuItem("inspect", "k", new MenuInspect(menuManager)));
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

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCursorMoved(Cursor cursor) {
		// TODO Auto-generated method stub

	}

}
