package core;

import java.awt.Graphics2D;
import java.util.ArrayList;

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

		ArrayList<Unit> units = menuManager.getCore().getWorld().getUnitsAt(coords.getTile());
		if (units == null)
			return;
		if (units.size() == 1)
			getSelectionResult(new SelectorToolTipOneLayer(menuManager, units.get(0).getInventory()));
	}

	@Override
	public void cursorMoved(Cursor c) {

	}

	@Override
	public void draw(Graphics2D g) {

	}

}
