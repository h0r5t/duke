package core;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Menu extends InputAdapter {

	protected HashMap<String, MenuText> menuTextMap;
	protected ArrayList<MenuText> menuTextList;
	protected MenuManager menuMgr;

	public Menu(MenuManager menuMgr) {
		this.menuMgr = menuMgr;
		menuTextMap = new HashMap<>();
		menuTextList = new ArrayList<>();
	}

	protected void addLine(String identifier, MenuText menuTxt) {
		menuTextMap.put(identifier, menuTxt);
		menuTextList.add(menuTxt);
	}

	protected MenuText getLine(String identifier) {
		return menuTextMap.get(identifier);
	}

	protected void removeLine(String identifier) {
		menuTextList.remove(menuTextMap.get(identifier));
		menuTextMap.remove(identifier);
	}

	protected HashMap<String, MenuText> getMenuTextMap() {
		return menuTextMap;
	}

	protected void selectThisLineOnly(String identifier) {
		for (String id : menuTextMap.keySet()) {
			if (id.equals(identifier))
				menuTextMap.get(id).select();
			else
				menuTextMap.get(id).deselect();
		}
	}

	protected void deselectAllLines() {
		for (String id : menuTextMap.keySet()) {
			menuTextMap.get(id).deselect();
		}
	}

	public ArrayList<MenuText> getMenuTextList() {
		return menuTextList;
	}

	public abstract void update();

	public abstract Menu getParentMenu();

	public ArrayList<MenuText> getMenuTexts() {
		return menuTextList;
	}

}
