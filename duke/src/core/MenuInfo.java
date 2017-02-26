package core;

import java.awt.Graphics2D;

public class MenuInfo extends Menu {

	public MenuInfo(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "info";
	}

	@Override
	public void start() {
		Coords3D coords = menuManager.getCursor().getCoords3D();
		if (coords == null)
			return;

		Unit u = menuManager.getCore().getWorld().getUnitAt(coords.getTile());
		if (u == null)
			return;

		getSelectionResult(new SelectorToolTipOneLayer(menuManager, u.getInventory()));
	}

	@Override
	public void cursorMoved(Cursor c) {

	}

	@Override
	public void draw(Graphics2D g) {

	}

}
