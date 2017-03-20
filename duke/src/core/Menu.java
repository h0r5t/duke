package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Menu extends InputAdapter implements Runnable {

	protected MenuManager menuManager;
	private Selector currentSelector;
	private ArrayList<MenuItem> mainItems;
	private MenuItem itemClicked;
	private MenuMainPane mainPane;
	private ArrayList<MenuItem> footerItems;
	private Font footerFont = new Font("Arial", Font.PLAIN, 18);

	public Menu(MenuManager menuManager) {
		this.menuManager = menuManager;
		this.mainItems = new ArrayList<>();
		this.footerItems = new ArrayList<>();
		mainPane = new MenuMainPane(this);
	}

	public Selector getCurrentSelector() {
		return currentSelector;
	}

	public void addItem(MenuItem i) {
		this.mainItems.add(i);
		mainPane.update();
	}

	public void addFooterItem(MenuItem i) {
		footerItems.add(i);
	}

	public ArrayList<MenuItem> getMenuItems() {
		return mainItems;
	}

	protected abstract void itemWasClicked(MenuItem i);

	protected void selectionChanged() {

	}

	public abstract String getName();

	protected Object getSelectionResult(Selector menuSelector) {
		currentSelector = menuSelector;
		Object result = null;
		while (true) {

			if (currentSelector.isDone()) {
				result = menuSelector.getResult();
				currentSelector = null;
				break;
			}

			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void run() {
		start();

		while (true) {
			if (itemClicked != null) {
				itemWasClicked(itemClicked);
				itemClicked = null;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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

			handleMenuItemInput(e);

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				MenuItem i = mainItems.get(mainPane.getSelectedItemIndex());

				if (i.isLink()) {
					menuManager.switchToMenu(i.getLink());
				} else {
					itemClicked = i;
				}
			}

			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				menuManager.goToLastMenu();
			}

			else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				mainPane.scrollDown();
			}

			else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				mainPane.scrollUp();
			}
		}

		else
			currentSelector.keyReleased(e);

	}

	private void handleMenuItemInput(KeyEvent e) {
		int counter = 0;
		for (MenuItem i : mainItems) {
			if (e.getKeyChar() == i.getHotkey().toLowerCase().toCharArray()[0]) {
				if (i.isLink()) {
					menuManager.switchToMenu(i.getLink());
				} else {
					itemClicked = i;
					mainPane.setSelectedItemIndex(counter);
				}
				break;
			}
			counter++;
		}

		for (MenuItem i : footerItems) {
			if (e.getKeyChar() == i.getHotkey().toLowerCase().toCharArray()[0]) {
				itemClicked = i;
				break;
			}
			counter++;
		}
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
		draw(g);
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(Settings.MENU_STARTX() - 30, 0, Settings.MENU_WIDTH + 40, Settings.GAME_FRAME_HEIGHT);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Settings.MENU_STARTX() - 30, 0, 10, Settings.GAME_FRAME_HEIGHT);

		mainPane.draw(g);

		int footerStartY = mainPane.MAIN_PANE_START_Y + mainPane.MAIN_PANE_LENGTH - 30;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Settings.MENU_STARTX() - 30, footerStartY, Settings.MENU_WIDTH + 40, 5);
		footerStartY += 30;
		g.setFont(footerFont);
		for (MenuItem i : footerItems) {
			i.draw(g, Settings.MENU_STARTX() + 100, footerStartY);
			footerStartY += 20;
		}
	}

}
