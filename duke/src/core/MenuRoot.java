package core;

import java.awt.event.KeyEvent;

public class MenuRoot extends Menu {

	public MenuRoot(MenuManager menuMgr) {
		super(menuMgr);
		addLine("M: move");
		addLine("R: mine");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_M) {
			menuMgr.goToMenu(new MenuMove(menuMgr));
		}

		else if (e.getKeyCode() == KeyEvent.VK_R) {
			menuMgr.goToMenu(new MenuMine(menuMgr));
		}
	}

}
