package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuMove extends Menu {

	public MenuMove(MenuManager menuMgr) {
		super(menuMgr);
		addLine("select target location...");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			TaskMoveSingleTarget task = new TaskMoveSingleTarget(
					menuMgr.getCursor().getTile());
			menuMgr.getCore().getTaskDistributor().addTask(task);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		TaskMoveSingleTarget task = new TaskMoveSingleTarget(
				menuMgr.getCursor().getTile());
		menuMgr.getCore().getTaskDistributor().addTask(task);
	}
}
