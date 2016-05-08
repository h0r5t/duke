package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputManager {

	private Core core;
	private InputControllerMain mainInputController;
	private DefaultHashMap<Integer, Boolean> keysPressed;
	private Cursor cursor;

	public InputManager(Core core) {
		this.core = core;
		keysPressed = new DefaultHashMap<Integer, Boolean>();
		mainInputController = new InputControllerMain(core);

		cursor = new Cursor(core, 5, 5);
	}

	public void update() {
		mainInputController.keysPressed(keysPressed);
		core.getMenuManager().getCurrentMenu().keysPressed(keysPressed);
	}

	public void keyPressed(KeyEvent e) {
		keysPressed.put(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		mainInputController.keyReleased(e);
		core.getMenuManager().getCurrentMenu().keyReleased(e);
		keysPressed.put(e.getKeyCode(), false);
	}

	public void mouseClicked(MouseEvent e) {
		try {
			mainInputController.mouseClicked(e);
			core.getMenuManager().getCurrentMenu().mouseClicked(e);
		} catch (Exception a) {
		}
	}

	public void mousePressed(MouseEvent e) {
		mainInputController.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		mainInputController.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e) {
		mainInputController.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		mainInputController.mouseMoved(e);
		core.getMenuManager().getCurrentMenu().mouseMoved(e);
	}

	public Cursor getCursor() {
		return cursor;
	}

}
