package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuMove extends Menu {

	public MenuMove(MenuManager menuMgr) {
		super(menuMgr);
		addLine("select target location...");
	}

	private void onEnter() {
		TaskMove task = new TaskMove(menuMgr.getCursor().getCoords3D());
		menuMgr.getCore().getTaskDistributor().addTask(task);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			onEnter();
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menuMgr.goToParentMenu();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onEnter();
	}

	@Override
	public void update() {

	}
}
