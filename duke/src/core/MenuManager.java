package core;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuManager extends InputAdapter {

	private Core core;
	private Menu currentMenu;

	public MenuManager(Core core) {
		this.core = core;
	}

	public Core getCore() {
		return core;
	}

	public Cursor getCursor() {
		return core.getInputManager().getCursor();
	}

	public void update() {
		if (currentMenu != null) {
			currentMenu.update();
		}
	}

	//
	// INPUT GOES THROUGH FROM INPUTMANAGER
	//

	public void draw(Graphics2D g) {

	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (currentMenu != null)
			currentMenu.keysPressed(keysPressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (currentMenu == null) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				currentMenu = new MenuMine(this);
				new Thread(currentMenu).start();
			}

		} else
			currentMenu.keyReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentMenu != null)
			currentMenu.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentMenu != null)
			currentMenu.mouseClicked(e);
	}

	@Override
	public void cursorMoved(Cursor c) {
		if (currentMenu != null)
			currentMenu.cursorMoved(c);
	}

}
