package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuStockpiles extends Menu {

	private enum Mode {
		MODE_NONE, MODE_NEW, MODE_REMOVE;
	}

	private Zone2D selArea;
	private Mode currentMode = Mode.MODE_NONE;

	public MenuStockpiles(MenuManager menuMgr) {
		super(menuMgr);
		addLine("esc", new MenuText("ESC", "back"));
		addLine("n", new MenuText("n", "new stockpile"));
		addLine("r", new MenuText("r", "remove stockpile"));
	}

	private void onEnter() {
		if (currentMode == Mode.MODE_NEW) {
			if (selArea == null) {
				Cursor cursor = menuMgr.getCursor();
				selArea = new Zone2D(cursor.getXpos(), cursor.getYpos(),
						cursor.getZpos(), SelectionType.TYPE_ZONE);
			} else {
				selArea.reset();
				Stockpile s = new Stockpile(selArea);
				menuMgr.getCore().getLogisticsManager().getStockPileManager()
						.addStockpile(s);
				selArea = null;
				currentMode = Mode.MODE_NONE;
			}
		} else if (currentMode == Mode.MODE_REMOVE) {
			Stockpile s = menuMgr.getCore().getLogisticsManager()
					.getStockPileManager()
					.getStockpileForPos(menuMgr.getCursor().getCoords3D());
			if (s != null)
				menuMgr.getCore().getLogisticsManager().getStockPileManager()
						.removeStockpile(s);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_N) {
			currentMode = Mode.MODE_NEW;
		}

		else if (e.getKeyCode() == KeyEvent.VK_R) {
			currentMode = Mode.MODE_REMOVE;
		}

		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			onEnter();
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (currentMode == Mode.MODE_NONE) {
				menuMgr.goToParentMenu();
			} else if (currentMode == Mode.MODE_NEW) {
				if (selArea == null) {
					currentMode = Mode.MODE_NONE;
				} else {
					selArea.reset();
					selArea = null;
				}
			} else if (currentMode == Mode.MODE_REMOVE) {
				currentMode = Mode.MODE_NONE;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onEnter();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentMode == Mode.MODE_NEW) {
			if (selArea != null)
				selArea.setEnd(menuMgr.getCursor().getXpos(),
						menuMgr.getCursor().getYpos());
		} else if (currentMode == Mode.MODE_REMOVE) {

		}
	}

	@Override
	public void cursorMoved(Cursor c) {
		if (currentMode == Mode.MODE_NEW) {
			if (selArea != null)
				selArea.setEnd(menuMgr.getCursor().getXpos(),
						menuMgr.getCursor().getYpos());
		} else if (currentMode == Mode.MODE_REMOVE) {

		}
	}

	@Override
	public void update() {
		if (currentMode == Mode.MODE_NEW) {
			selectThisLineOnly("n");
		} else if (currentMode == Mode.MODE_REMOVE) {
			selectThisLineOnly("r");
		} else if (currentMode == Mode.MODE_NONE) {
			deselectAllLines();
		}

	}

	@Override
	public Menu getParentMenu() {
		return new MenuZones(menuMgr);
	}

}
