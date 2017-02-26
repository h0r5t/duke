package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class MenuSelector extends InputAdapter {

	protected MenuManager menuManager;
	private boolean isDone;

	public MenuSelector(MenuManager menuManager) {
		this.menuManager = menuManager;
		this.isDone = false;
	}

	protected void finish() {
		isDone = true;
	}

	public abstract void onEscape();

	public abstract void onEnter();

	public boolean isDone() {
		return isDone;
	}

	public abstract Object getResult();

	public abstract void update();

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			onEnter();
		}

		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			onEscape();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		onEnter();
	}
}
