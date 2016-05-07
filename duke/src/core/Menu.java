package core;

import java.util.ArrayList;

public abstract class Menu extends InputAdapter {

	private ArrayList<String> menuText;
	protected MenuManager menuMgr;

	public Menu(MenuManager menuMgr) {
		this.menuMgr = menuMgr;
		menuText = new ArrayList<String>();
	}

	protected void addLine(String text) {
		menuText.add(text);
	}

	protected void removeLine(String text) {
		menuText.remove(text);
	}

	public ArrayList<String> getMenuText() {
		return menuText;
	}

}
