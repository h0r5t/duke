package core;

import java.awt.event.KeyEvent;

public class MenuRoot extends Menu {

	public MenuRoot(MenuManager menuMgr) {
		super(menuMgr);
		addLine("m: move");
		addLine("r: mine/remove trees");
		addLine("t: tasks");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_M) {
			menuMgr.goToMenu(new MenuMove(menuMgr));
		}

		else if (e.getKeyCode() == KeyEvent.VK_R) {
			menuMgr.goToMenu(new MenuMine(menuMgr));
		}

		else if (e.getKeyCode() == KeyEvent.VK_T) {
			menuMgr.goToMenu(new MenuTasksView(menuMgr));
		}
	}

	@Override
	public void update() {

	}

}
