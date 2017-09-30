package core;

import java.awt.Graphics2D;

public class MenuBuildingTest extends Menu {

	public MenuBuildingTest(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "test building";
	}

	@Override
	public void start() {
		Coords3D location = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
		TaskBuild tb = new TaskBuild(new BuildingCraftingTable(location), menuManager.getCore().getLogisticsManager());
		menuManager.getCore().getTaskDistributor().addTask(tb);
	}

	@Override
	public void draw(Graphics2D g) {

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
