package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuMine extends Menu {

	private AreaSelection areaSelection;
	private boolean cursorMoved = false;

	public MenuMine(MenuManager menuMgr) {
		super(menuMgr);
		addLine("select area to mine...");
	}

	private void onEnter() {
		if (areaSelection == null) {
			Cursor cursor = menuMgr.getCursor();
			areaSelection = new AreaSelection(cursor.getXpos(), cursor.getYpos(), cursor.getZpos());
		} else {
			createMiningTaskGroup();
			areaSelection = null;
			menuMgr.goToParentMenu();
		}
	}

	private void createMiningTaskGroup() {
		TaskGroupMining taskGroup = new TaskGroupMining(areaSelection);
		menuMgr.getCore().getTaskDistributor().addTaskGroup(taskGroup);
	}

	private void selectorMoved() {
		if (areaSelection != null) {
			areaSelection.setEnd(menuMgr.getCursor().getXpos(), menuMgr.getCursor().getYpos());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			onEnter();
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (areaSelection == null)
				menuMgr.goToParentMenu();
			else {
				areaSelection.reset();
				areaSelection = null;
			}
		}
	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		cursorMoved = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onEnter();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		cursorMoved = true;
	}

	@Override
	public void update() {
		if (cursorMoved) {
			selectorMoved();
			cursorMoved = false;
		}
	}

}
