package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputControllerMain extends InputAdapter {

	private Core core;
	private ViewManager viewManager;

	public InputControllerMain(Core core) {
		this.core = core;
		this.viewManager = core.getViewManager();
	}

	public void update() {

	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (keysPressed.getDefault(KeyEvent.VK_D, false)) {
			viewManager.shiftScreenX(Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_A, false)) {
			viewManager.shiftScreenX(-Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_S, false)) {
			viewManager.shiftScreenY(Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_W, false)) {
			viewManager.shiftScreenY(-Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_RIGHT, false))
			core.getInputManager().getCursor().moveX(1);
		if (keysPressed.getDefault(KeyEvent.VK_LEFT, false))
			core.getInputManager().getCursor().moveX(-1);
		if (keysPressed.getDefault(KeyEvent.VK_UP, false))
			core.getInputManager().getCursor().moveY(-1);
		if (keysPressed.getDefault(KeyEvent.VK_DOWN, false))
			core.getInputManager().getCursor().moveY(1);

		// if (lastEvent != null)
		// mouseMoved(lastEvent);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F1)
			Settings.DRAW_TILE_BORDERS = !Settings.DRAW_TILE_BORDERS;

		if (e.getKeyCode() == KeyEvent.VK_X)
			core.getViewManager().moveZ(1);
		if (e.getKeyCode() == KeyEvent.VK_Y)
			core.getViewManager().moveZ(-1);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// lastEvent = e;
		Tile tile = viewManager.getTileFromScreenPos(e.getX(), e.getY());
		if (tile == null)
			return;
		core.getInputManager().getCursor().setToTile(tile);
	}

}
