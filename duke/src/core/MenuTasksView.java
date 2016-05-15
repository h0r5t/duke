package core;

import java.awt.event.KeyEvent;

public class MenuTasksView extends Menu {

	public MenuTasksView(MenuManager menuMgr) {
		super(menuMgr);
	}

	@Override
	public void update() {
		setMenuText(menuMgr.getCore().getTaskDistributor().getTasksInfoList());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menuMgr.goToParentMenu();
		}
	}

	@Override
	public Menu getParentMenu() {
		return new MenuRoot(menuMgr);
	}

}
