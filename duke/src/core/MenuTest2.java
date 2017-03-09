package core;

import java.awt.Graphics2D;

public class MenuTest2 extends Menu {

	public MenuTest2(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "air";
	}

	@Override
	public void start() {
		Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_DESIGNATION));
		for (Coords3D c : zone.getCoords()) {
			Core.getWorld().setTile(new TileAir(c.getX(), c.getY(), c.getZ()));
		}

	}

	@Override
	public void draw(Graphics2D g) {

	}

}
