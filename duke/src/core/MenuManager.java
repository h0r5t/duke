package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuManager {

	private Core core;
	private Menu currentMenu;
	private Menu parentMenu;

	public MenuManager(Core core) {
		this.core = core;
		currentMenu = new MenuRoot(this);
		parentMenu = currentMenu;
	}

	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void goToMenu(Menu newMenu) {
		parentMenu = currentMenu;
		currentMenu = newMenu;
	}

	public void goToParentMenu() {
		currentMenu = parentMenu;
	}

	public Cursor getCursor() {
		return core.getInputManager().getCursor();
	}

	public Core getCore() {
		return core;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(Settings.MENU_STARTX(), 0, Settings.MENU_WIDTH,
				Settings.GAME_FRAME_HEIGHT);

		g.setColor(Color.LIGHT_GRAY);
		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);

		int x = Settings.MENU_STARTX() + 20;
		int y = 40;
		for (String s : currentMenu.getMenuText()) {
			g.drawString(s, x, y);
			y += 25;
		}
	}

	public void update() {
		currentMenu.update();
	}

}
