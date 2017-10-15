package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CameraAndCursorController extends InputAdapter {

	private Core core;
	private int fastCursorMoveCooldown = 200;

	public CameraAndCursorController(Core core) {
		this.core = core;
	}

	public void update() {
	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (keysPressed.getDefault(KeyEvent.VK_RIGHT, false)) {
			Core.getViewManager().getGameCamera().moveRight();
		}

		if (keysPressed.getDefault(KeyEvent.VK_LEFT, false)) {
			Core.getViewManager().getGameCamera().moveLeft();
		}

		if (keysPressed.getDefault(KeyEvent.VK_DOWN, false)) {
			Core.getViewManager().getGameCamera().moveDown();
		}

		if (keysPressed.getDefault(KeyEvent.VK_UP, false)) {
			Core.getViewManager().getGameCamera().moveUp();
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
			Core.getViewManager().getGameCamera().moveZ(1);
		if (e.getKeyCode() == KeyEvent.VK_Y)
			Core.getViewManager().getGameCamera().moveZ(-1);

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			Core.getViewManager().getGameCamera().moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			Core.getViewManager().getGameCamera().moveDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			Core.getViewManager().getGameCamera().moveLeft();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			Core.getViewManager().getGameCamera().moveUp();
		}

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
		// Tile tile = viewManager.getTileFromScreenPos(e.getX(), e.getY());
		// if (tile == null)
		// return;
		// core.getInputManager().getCursor().setToTile(tile);
	}

}
