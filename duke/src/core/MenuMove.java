package core;

import java.awt.Graphics2D;

public class MenuMove extends Menu {

	public MenuMove(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "move";
	}

	@Override
	public void start() {
		Coords3D targetCoords = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
		TaskMove moveTask = new TaskMove(targetCoords);
		menuManager.getCore().getTaskDistributor().addTask(moveTask);
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
