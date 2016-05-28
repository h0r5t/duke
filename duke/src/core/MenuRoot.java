package core;

import java.awt.event.KeyEvent;

public class MenuRoot extends Menu {

	public MenuRoot(MenuManager menuMgr) {
		super(menuMgr);
		addLine("m", new MenuText("m", "move"));
		addLine("r", new MenuText("r", "mine/remove trees"));
		addLine("t", new MenuText("t", "view tasks"));
		addLine("z", new MenuText("z", "zones"));
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

		else if (e.getKeyCode() == KeyEvent.VK_Z) {
			menuMgr.goToMenu(new MenuZones(menuMgr));
		}
	}

	@Override
	public void update() {

	}

	@Override
	public Menu getParentMenu() {
		return this;
	}

}
