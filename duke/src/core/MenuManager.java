package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MenuManager extends InputAdapter {

	private Core core;
	private Menu currentMenu;
	private ArrayList<Menu> menuHistory;

	public MenuManager(Core core) {
		this.core = core;
		currentMenu = new MenuMain(this);
		this.menuHistory = new ArrayList<>();
	}

	public Core getCore() {
		return core;
	}

	public void switchToMenu(Menu m) {
		if (!(currentMenu instanceof MenuMain))
			menuHistory.add(currentMenu);
		currentMenu = m;
		new Thread(currentMenu).start();
	}

	public void goToLastMenu() {
		if (menuHistory.size() > 0) {
			currentMenu = menuHistory.remove(menuHistory.size() - 1);
		} else {
			currentMenu = new MenuMain(this);
			new Thread(currentMenu).start();
		}

	}

	public Cursor getCursor() {
		return core.getInputManager().getCursor();
	}

	public void update() {
		if (currentMenu != null) {
			currentMenu.update();
		}
	}

	public void draw(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.setColor(Color.WHITE);
		g.drawString("height:" + (Settings.WORLD_DEPTH - getCursor().getZpos()), 10, 30);
		g.drawString("fluid:" + getCore().getFluidManager().getCalculations(), 10, 45);

		if (currentMenu != null)
			currentMenu.drawMenu(g);
	}

	//
	// INPUT GOES THROUGH FROM INPUTMANAGER
	//

	@Override
	public void keysPressed(DefaultHashMap<Integer, Boolean> keysPressed) {
		if (currentMenu != null)
			currentMenu.keysPressed(keysPressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (currentMenu != null)
			currentMenu.keyReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentMenu != null)
			currentMenu.mouseMoved(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentMenu != null)
			currentMenu.mouseClicked(e);
	}

	@Override
	public void cursorMoved(Cursor c) {
		if (currentMenu != null)
			currentMenu.cursorMoved(c);
	}

	public void resetMenu() {
		currentMenu = null;
	}

}
