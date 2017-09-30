package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CameraAndCursorController extends InputAdapter {

	private Core core;
	private ViewManager viewManager;
	private int fastCursorMoveCooldown = 200;

	public CameraAndCursorController(Core core) {
		this.core = core;
		this.viewManager = core.getViewManager();
	}

	public void update() {
	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (keysPressed.getDefault(KeyEvent.VK_RIGHT, false)) {
			viewManager.shiftScreenX(Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_LEFT, false)) {
			viewManager.shiftScreenX(-Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_DOWN, false)) {
			viewManager.shiftScreenY(Settings.SHIFT_SPEED);
		}

		if (keysPressed.getDefault(KeyEvent.VK_UP, false)) {
			viewManager.shiftScreenY(-Settings.SHIFT_SPEED);
		}

		boolean reset = true;

		if (keysPressed.getDefault(KeyEvent.VK_D, false)) {
			if (fastCursorMoveCooldown == 0)
				core.getInputManager().getCursor().moveX(1);
			else
				fastCursorMoveCooldown--;
			reset = false;
		}

		if (keysPressed.getDefault(KeyEvent.VK_A, false)) {
			if (fastCursorMoveCooldown == 0)
				core.getInputManager().getCursor().moveX(-1);
			else
				fastCursorMoveCooldown--;
			reset = false;
		}

		if (keysPressed.getDefault(KeyEvent.VK_W, false)) {
			if (fastCursorMoveCooldown == 0)
				core.getInputManager().getCursor().moveY(-1);
			else
				fastCursorMoveCooldown--;
			reset = false;
		}

		if (keysPressed.getDefault(KeyEvent.VK_S, false)) {
			if (fastCursorMoveCooldown == 0)
				core.getInputManager().getCursor().moveY(1);
			else
				fastCursorMoveCooldown--;
			reset = false;
		}

		if (reset)
			fastCursorMoveCooldown = 200;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F1)
			Settings.DRAW_TILE_BORDERS = !Settings.DRAW_TILE_BORDERS;

		if (e.getKeyCode() == KeyEvent.VK_X)
			core.getViewManager().moveZ(1);
		if (e.getKeyCode() == KeyEvent.VK_Y)
			core.getViewManager().moveZ(-1);

		int moveAmount = 1;
		if (e.isShiftDown())
			moveAmount = 5;
		if (e.getKeyCode() == KeyEvent.VK_D)
			core.getInputManager().getCursor().moveX(moveAmount);
		if (e.getKeyCode() == KeyEvent.VK_A)
			core.getInputManager().getCursor().moveX(-moveAmount);
		if (e.getKeyCode() == KeyEvent.VK_W)
			core.getInputManager().getCursor().moveY(-moveAmount);
		if (e.getKeyCode() == KeyEvent.VK_S)
			core.getInputManager().getCursor().moveY(moveAmount);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Tile tile = viewManager.getTileFromScreenPos(e.getX(), e.getY());
		if (tile == null)
			return;
		core.getInputManager().getCursor().setToTile(tile);
	}

}
