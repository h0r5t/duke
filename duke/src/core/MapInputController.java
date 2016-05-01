package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MapInputController extends InputController {

	private Core core;
	private ViewManager viewManager;

	public MapInputController(Core core) {
		this.core = core;
		this.viewManager = core.getViewManager();
	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (keysPressed.getDefault(KeyEvent.VK_D, false))
			viewManager.shiftScreenX(Settings.SHIFT_SPEED);
		if (keysPressed.getDefault(KeyEvent.VK_A, false))
			viewManager.shiftScreenX(Settings.SHIFT_SPEED * -1);
		if (keysPressed.getDefault(KeyEvent.VK_S, false))
			viewManager.shiftScreenY(Settings.SHIFT_SPEED);
		if (keysPressed.getDefault(KeyEvent.VK_W, false))
			viewManager.shiftScreenY(Settings.SHIFT_SPEED * -1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F1)
			Settings.DRAW_TILE_BORDERS = !Settings.DRAW_TILE_BORDERS;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tile tile = viewManager.getTileFromScreenPos(e.getX(), e.getY());
	}

}
