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

	public MenuManager(Core core) {
		this.core = core;
	}
	
	public Cursor getCursor() {
		return core.getInputManager().getCursor();
	}

	public void update() {
		currentMenu.update();
		for (MenuGroup mg : menuGroups) {
			mg.update();
		}
	}
	
	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		currentMenu.
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentMenu
	}

}
