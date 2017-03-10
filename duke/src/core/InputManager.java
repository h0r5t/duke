package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputManager implements Runnable {

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
		core.getMenuManager().keysPressed(keysPressed);
	}

	public void keyPressed(KeyEvent e) {
		keysPressed.put(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		mainInputController.keyReleased(e);
		core.getMenuManager().keyReleased(e);
		keysPressed.put(e.getKeyCode(), false);
	}

	public void mouseClicked(MouseEvent e) {
		try {
			mainInputController.mouseClicked(e);
			core.getMenuManager().mouseClicked(e);
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
		core.getMenuManager().mouseMoved(e);
	}

	public void cursorMoved() {
		core.getMenuManager().cursorMoved(cursor);
	}

	public Cursor getCursor() {
		return cursor;
	}

	@Override
	public void run() {
		while (true) {
			update();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
