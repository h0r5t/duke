package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuMainPane {

	private static final int MAX_ITEMS_IN_SCREEN = 25;
	public static final int MAIN_PANE_START_Y = 100;
	public static final int MAIN_PANE_LENGTH = 800;
	private static final int SCROLL_BAR_DEFAULT_START_Y = MAIN_PANE_START_Y - 20;
	private static final int SCROLL_BAR_DEFAULT_LENGTH = MAIN_PANE_LENGTH - 50;

	private Menu menu;
	private int selectedItemIndex = 0;
	private Font font;
	private Font fontBold;
	private int scrollStartIndex = 0;
	private int scrollBarSize;
	private int scrollBarDelta;

	public MenuMainPane(Menu menu) {
		this.menu = menu;
		this.scrollStartIndex = 0;
		this.font = new Font("Arial", Font.PLAIN, 22);
		this.fontBold = new Font("Arial", Font.BOLD, 26);

	}

	public void update() {
		updateScrollBar();
	}

	private void updateScrollBar() {
		if (menu.getMenuItems().size() <= MAX_ITEMS_IN_SCREEN) {
			scrollBarSize = SCROLL_BAR_DEFAULT_LENGTH;
			scrollBarDelta = 0;
		} else {
			scrollBarSize = (int) ((double) SCROLL_BAR_DEFAULT_LENGTH
					/ ((double) menu.getMenuItems().size() / (double) MAX_ITEMS_IN_SCREEN));
			scrollBarDelta = (int) ((double) (SCROLL_BAR_DEFAULT_LENGTH - scrollBarSize)
					/ (double) (menu.getMenuItems().size() - MAX_ITEMS_IN_SCREEN));
		}
	}

	public void scrollDown() {
		selectedItemIndex++;
		if (selectedItemIndex > menu.getMenuItems().size() - 1)
			selectedItemIndex = menu.getMenuItems().size() - 1;

		if (selectedItemIndex >= scrollStartIndex + MAX_ITEMS_IN_SCREEN)
			scrollStartIndex++;
	}

	public void scrollUp() {
		selectedItemIndex--;
		if (selectedItemIndex < 0)
			selectedItemIndex = 0;

		if (selectedItemIndex < scrollStartIndex)
			scrollStartIndex--;
		if (scrollStartIndex < 0)
			scrollStartIndex = 0;
	}

	public int getSelectedItemIndex() {
		return selectedItemIndex;
	}

	public void setSelectedItemIndex(int selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(Settings.MENU_STARTX() + 400, SCROLL_BAR_DEFAULT_START_Y + scrollStartIndex * scrollBarDelta, 10,
				scrollBarSize);

		g.setFont(fontBold);
		int x = Settings.MENU_STARTX() + 20;
		g.setColor(Color.WHITE);
		g.drawString(menu.getName(), x, 35);
		g.setFont(font);
		int y = MAIN_PANE_START_Y;
		for (int i = scrollStartIndex; i < scrollStartIndex + MAX_ITEMS_IN_SCREEN; i++) {
			if (i >= menu.getMenuItems().size())
				break;
			MenuItem item = menu.getMenuItems().get(i);
			if (i == selectedItemIndex) {
				g.setFont(fontBold);
				item.draw(g, x, y);
				g.setFont(font);
			} else {
				item.draw(g, x, y);
			}

			y += 30;
		}
	}

}
