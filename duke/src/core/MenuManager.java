package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuManager {

	private Core core;
	private Menu currentMenu;
	private int currentLoopTime;

	public MenuManager(Core core) {
		this.core = core;
		currentMenu = new MenuRoot(this);
		currentLoopTime = 0;
	}

	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void goToMenu(Menu newMenu) {
		currentMenu = newMenu;
	}

	public void goToParentMenu() {
		currentMenu = currentMenu.getParentMenu();
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

		g.setColor(Color.YELLOW);
		Font font = new Font("Arial", Font.PLAIN, 16);
		g.setFont(font);

		int x = Settings.MENU_STARTX() + 100;
		int y = 20;
		g.drawString(currentLoopTime + " ms", x, y);

		g.setColor(Color.LIGHT_GRAY);
		font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);

		x = Settings.MENU_STARTX() + 20;
		y = 100;
		for (String s : currentMenu.getMenuText()) {
			g.drawString(s, x, y);
			y += 25;
		}
	}

	public void update() {
		currentMenu.update();
	}

	public void setLoopTime(int time) {
		currentLoopTime = time;
	}

}
