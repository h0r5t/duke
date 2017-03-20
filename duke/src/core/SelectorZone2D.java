package core;

import java.awt.event.MouseEvent;

public class SelectorZone2D extends Selector {

	private Zone2D zone;
	private MenuManager menuManager;
	private SelectionType selectionType;
	private boolean cMoved = false;

	public SelectorZone2D(MenuManager menuManager, SelectionType selectionType) {
		super(menuManager);
		this.menuManager = menuManager;
		this.selectionType = selectionType;
		menuManager.getCursor().setSelectionType(SelectionType.TYPE_DESIGNATION);
	}

	@Override
	public Object getResult() {
		return zone;
	}

	@Override
	public void onEnter() {
		Cursor cursor = menuManager.getCursor();
		if (zone == null) {
			zone = new Zone2D(cursor.getXpos(), cursor.getYpos(), cursor.getZpos(), selectionType);
		} else {
			zone.reset();
			cursor.resetSelectionType();
			finish();
		}
	}

	@Override
	public void onEscape() {
		if (zone != null)
			zone.reset();
		zone = null;
		finish();
		menuManager.getCursor().resetSelectionType();
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
