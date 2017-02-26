package core;

import java.awt.Graphics;

public abstract class MenuSelectorDrawable extends MenuSelector {

	public MenuSelectorDrawable(MenuManager menuManager) {
		super(menuManager);
	}

	public abstract void draw(Graphics g);

}
