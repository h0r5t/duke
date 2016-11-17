package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuManager extends InputAdapter {

	private Core core;
	private Menu currentMenu;
	private ArrayList<MenuGroup> menuGroups;
	private MenuManager self = this;
	private HashMap<Integer, String> hotkeyMap;

	public MenuManager(Core core) {
		this.core = core;
		currentMenu = new MenuRoot(this);
		this.menuGroups = new ArrayList<MenuGroup>();
		this.hotkeyMap = new HashMap<>();
		createShortcuts();
		createMenus();
	}

	private void createShortcuts() {
		hotkeyMap.put(KeyEvent.VK_M, "Mining");
	}

	private void createMenus() {
		MenuGroup generalGroup = new MenuGroup("General");
		MenuElement elementMove = new MenuElement("Move", new ElementClickHandler());
		generalGroup.addElement(elementMove);
		menuGroups.add(generalGroup);

		MenuGroup designateGroup = new MenuGroup("Designate");
		MenuElement elementMining = new MenuElement("Mining", new ElementClickHandler());
		designateGroup.addElement(elementMining);
		menuGroups.add(designateGroup);

		MenuGroup zonesGroup = new MenuGroup("Zones");
		MenuElement elementNewStockpile = new MenuElement("New Stockpile", new ElementClickHandler());
		zonesGroup.addElement(elementNewStockpile);
		MenuElement elementRemoveStockpile = new MenuElement("Remove Stockpile", new ElementClickHandler());
		zonesGroup.addElement(elementRemoveStockpile);
		menuGroups.add(zonesGroup);
	}

	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void goToMenu(Menu newMenu) {
		currentMenu = newMenu;
	}

	public Cursor getCursor() {
		return core.getInputManager().getCursor();
	}

	public Core getCore() {
		return core;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, Settings.MENU_STARTY(), Settings.GAME_FRAME_WIDTH, Settings.MENU_HEIGHT);

		int posX = 0;
		for (MenuGroup mg : menuGroups) {
			mg.draw(g, posX, Settings.MENU_STARTY());
			posX += Settings.MENU_GROUP_WIDTH;
		}
	}

	public void update() {
		currentMenu.update();
		for (MenuGroup mg : menuGroups) {
			mg.update();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentMenu.mouseMoved(e);
		for (MenuGroup mg : menuGroups) {
			mg.setMouseIsOn(false);
		}
		MenuGroup g;
		int x = e.getX() / Settings.MENU_GROUP_WIDTH;
		if (x >= menuGroups.size()) {
			return;
		}
		g = menuGroups.get(x);
		if (e.getY() > g.getAreaStart()) {
			g.setMouseIsOn(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		currentMenu.mouseClicked(e);
		MenuGroup g;
		int x = e.getX() / Settings.MENU_GROUP_WIDTH;
		if (x >= menuGroups.size()) {
			return;
		}
		g = menuGroups.get(x);
		if (e.getY() > g.getAreaStart()) {
			g.handleClick(e.getX(), e.getY());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentMenu.keyReleased(e);
		if (hotkeyMap.keySet().contains(e.getKeyCode())) {
			new ElementClickHandler().eventFired(hotkeyMap.get(e.getKeyCode()));
		}
	}

	private class ElementClickHandler implements OnClickListener {

		@Override
		public void eventFired(String id) {
			if (id.equals("Move")) {
				goToMenu(new MenuMove(self));
			} else if (id.equals("Mining")) {
				goToMenu(new MenuMine(self));
			}
		}

	}

}
