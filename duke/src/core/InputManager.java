package core;

import java.awt.event.KeyEvent;

public class InputManager {

	private ViewManager viewManager;
	private DefaultHashMap<Integer, Boolean> keysPressed;

	public InputManager(ViewManager viewManager) {
		this.viewManager = viewManager;
		keysPressed = new DefaultHashMap<Integer, Boolean>();
	}

	public void update() {
		if (keysPressed.getDefault(KeyEvent.VK_D, false))
			viewManager.shiftScreenX(Settings.SHIFT_SPEED);
		if (keysPressed.getDefault(KeyEvent.VK_A, false))
			viewManager.shiftScreenX(Settings.SHIFT_SPEED * -1);
		if (keysPressed.getDefault(KeyEvent.VK_S, false))
			viewManager.shiftScreenY(Settings.SHIFT_SPEED);
		if (keysPressed.getDefault(KeyEvent.VK_W, false))
			viewManager.shiftScreenY(Settings.SHIFT_SPEED * -1);
	}

	public void keyPressed(KeyEvent e) {
		keysPressed.put(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		keysPressed.put(e.getKeyCode(), false);
	}
}
