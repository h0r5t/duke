package core;

import java.awt.Graphics2D;

public class MenuTest extends Menu {

	public MenuTest(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "place";
	}

	@Override
	public void start() {
		Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_DESIGNATION));
		for (Coords3D c : zone.getCoords()) {
			Core.getWorld().wasMined(c);
		}

	}

	@Override
	public void draw(Graphics2D g) {

	}

}
