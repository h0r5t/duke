package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Zone2DSelector extends MenuSelector {

	private boolean isDone;
	private Zone2D zone;
	private MenuManager menuManager;
	private SelectionType selectionType;
	private boolean cMoved = false;

	public Zone2DSelector(MenuManager menuManager, SelectionType selectionType) {
		super(menuManager);
		isDone = false;
		this.menuManager = menuManager;
		this.selectionType = selectionType;
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

	@Override
	public Object[] getResults() {
		return new Object[] { zone };
	}

	private void onEnter() {
		if (zone == null) {
			Cursor cursor = menuManager.getCursor();
			zone = new Zone2D(cursor.getXpos(), cursor.getYpos(), cursor.getZpos(), selectionType);
		} else {
			if (selectionType == SelectionType.TYPE_DESIGNATION)
				zone.removeNonMineables();
			isDone = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			onEnter();
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// zone = null;
			isDone = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onEnter();
	}

	private void cursorMoved() {
		if (zone != null) {
			zone.setEnd(menuManager.getCursor().getXpos(), menuManager.getCursor().getYpos());
		}
	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		cMoved = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		cMoved = true;
	}

	@Override
	public void update() {
		if (cMoved)
			cursorMoved();
		cMoved = false;
	}

}
