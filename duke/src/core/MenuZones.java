package core;

import java.awt.event.KeyEvent;

public class MenuZones extends Menu {

	public MenuZones(MenuManager menuMgr) {
		super(menuMgr);
		addLine("esc", new MenuText("ESC", "back"));
		addLine("p", new MenuText("p", "stockpiles"));
		menuMgr.getCore().getLogisticsManager().getStockPileManager().setStockpileMarkers(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {
			menuMgr.goToMenu(new MenuStockpiles(menuMgr));
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			menuMgr.getCore().getLogisticsManager().getStockPileManager().setStockpileMarkers(false);
			menuMgr.goToParentMenu();
		}
	}

	@Override
	public void update() {

	}

	@Override
	public Menu getParentMenu() {
		return new MenuRoot(menuMgr);
	}

}
