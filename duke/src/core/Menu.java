package core;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Menu extends InputAdapter implements Runnable {

	protected MenuManager menuManager;
	private MenuSelector currentSelector;

	public Menu(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public MenuSelector getCurrentSelector() {
		return currentSelector;
	}

	public abstract String getName();

	protected Object getSelectionResult(MenuSelector menuSelector) {
		currentSelector = menuSelector;
		Object result = null;
		while (true) {

			if (currentSelector.isDone()) {
				result = menuSelector.getResult();
				currentSelector = null;
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void run() {
		start();
		menuManager.resetMenu();
	}

	public abstract void start();

	public void update() {
		if (currentSelector != null) {
			currentSelector.update();
		}
	}

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (currentSelector != null)
			currentSelector.keysPressed(keysPressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (currentSelector == null) {
			// menu hotkeys

		} else
			currentSelector.keyReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentSelector != null)
			currentSelector.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentSelector != null)
			currentSelector.mouseClicked(e);
	}

	@Override
	public void cursorMoved(Cursor c) {
		if (currentSelector != null)
			currentSelector.cursorMoved(c);
	}

	public void drawMenu(Graphics2D g) {
		if (currentSelector != null) {
			if (currentSelector instanceof MenuSelectorDrawable)
				((MenuSelectorDrawable) currentSelector).draw(g);
		}
		draw(g);
	}

	public abstract void draw(Graphics2D g);

}
