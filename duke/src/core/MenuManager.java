package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuManager extends InputAdapter {

	private Core core;
	private Menu currentMenu;

	public MenuManager(Core core) {
		this.core = core;
	}

	public Core getCore() {
		return core;
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

		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(Color.WHITE);
		g.drawString("" + getCursor().getZpos(), 10, 30);

		if (currentMenu != null) {
			g.setColor(Color.WHITE);
			Font font = new Font("Arial", Font.BOLD, 14);
			g.drawString(currentMenu.getName(), 10, Settings.GAME_FRAME_HEIGHT - 50);

			currentMenu.drawMenu(g);
		}
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
		if (currentMenu == null) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				setMenu(new MenuMine(this));
			} else if (e.getKeyCode() == KeyEvent.VK_M) {
				setMenu(new MenuMove(this));
			} else if (e.getKeyCode() == KeyEvent.VK_Z) {
				setMenu(new MenuZones(this));
			} else if (e.getKeyCode() == KeyEvent.VK_Q) {
				setMenu(new MenuInfo(this));
			} else if (e.getKeyCode() == KeyEvent.VK_P) {
				setMenu(new MenuProjectileTest(this));
			} else if (e.getKeyCode() == KeyEvent.VK_B) {
				setMenu(new MenuBuildingTest(this));
			}

		} else
			currentMenu.keyReleased(e);
	}

	private void setMenu(Menu m) {
		currentMenu = m;
		new Thread(currentMenu).start();
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
