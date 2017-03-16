package core;

import java.awt.Graphics2D;

public class MenuTest extends Menu {

	public MenuTest(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "chop tree";
	}

	@Override
	public void start() {
		Coords3D c = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
		TaskChopTree t = new TaskChopTree(c);
		menuManager.getCore().getTaskDistributor().addTask(t);
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
