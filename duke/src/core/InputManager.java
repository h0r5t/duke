package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InputManager {

	private ArrayList<InputController> activeInputControllers;
	private DefaultHashMap<Integer, Boolean> keysPressed;
	private Cursor cursor;

	public InputManager(Core core) {
		keysPressed = new DefaultHashMap<Integer, Boolean>();
		activeInputControllers = new ArrayList<InputController>();
		activeInputControllers.add(new InputControllerMain(core));

		cursor = new Cursor(core, 5, 5);
	}

	public void addInputController(InputController c) {
		activeInputControllers.add(c);
	}

	public void removeInputController(InputController c) {
		activeInputControllers.remove(c);
	}

	public void update() {
		for (InputController controller : activeInputControllers) {
			controller.keysPressed(keysPressed);
		}
	}

	public void keyPressed(KeyEvent e) {
		keysPressed.put(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		for (InputController controller : activeInputControllers) {
			controller.keyReleased(e);
		}
		keysPressed.put(e.getKeyCode(), false);
	}

	public void mouseClicked(MouseEvent e) {
		try {
			for (InputController controller : activeInputControllers) {
				controller.mouseClicked(e);
			}
		} catch (Exception a) {
		}
	}

	public void mousePressed(MouseEvent e) {
		for (InputController controller : activeInputControllers) {
			controller.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		for (InputController controller : activeInputControllers) {
			controller.mouseReleased(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		for (InputController controller : activeInputControllers) {
			controller.mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (InputController controller : activeInputControllers) {
			controller.mouseMoved(e);
		}
	}

	public Cursor getCursor() {
		return cursor;
	}

}
