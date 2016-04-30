package core;

import java.awt.event.MouseEvent;

public class InputControllerUnitSelected extends InputController {

	private Core core;
	private Unit selectedUnit;
	private TilePath currentPath;
	private MouseEvent lastEvent;

	public InputControllerUnitSelected(Core core, Unit u) {
		this.core = core;
		this.selectedUnit = u;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastEvent = e;
		if (currentPath != null)
			currentPath.destroy();
		Tile to = core.getViewManager().getTileFromScreenPos(e.getX(),
				e.getY());
		Tile from = Core.getWorld().getTile(selectedUnit.getX(),
				selectedUnit.getY());
		currentPath = core.getPathFinder().findPath(from, to);
	}
}
