package core;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuTasksView extends Menu {

	public MenuTasksView(MenuManager menuMgr) {
		super(menuMgr);
		addLine("esc", new MenuText("ESC", "back"));
	}

	@Override
	public void update() {
		setMenuTexts(menuMgr.getCore().getTaskDistributor().getTasksInfoList());
	}

	private void setMenuTexts(ArrayList<MenuText> tasksInfoList) {
		getMenuTextMap().clear();
		getMenuTextList().clear();
		addLine("esc", new MenuText("ESC", "back"));

		int c = 0;
		for (MenuText t : tasksInfoList) {
			addLine(c + "", t);
			c++;
		}
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
