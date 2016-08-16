package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.UIAnimationSlideUp.Mode;

public class MenuGroup implements Visual {

	private String id;
	private ArrayList<MenuElement> menuElements;
	private UIAnimationSlideUp slideUpAnim;

	public MenuGroup(String id) {
		this.id = id;
		this.menuElements = new ArrayList<MenuElement>();
	}

	private void createSlideUpAnim(int posX, int posY) {
		int height = Settings.MENU_ELEMENT_HEIGHT * menuElements.size();
		Rectangle source = new Rectangle(posX, posY + 50,
				Settings.MENU_GROUP_WIDTH, height);
		Rectangle target = new Rectangle(posX, posY - height,
				Settings.MENU_GROUP_WIDTH, height);
		this.slideUpAnim = new UIAnimationSlideUp(source, target);
	}

	public void handleClick(int x, int y) {
		if (slideUpAnim.isDone()) {
			if (y < Settings.MENU_STARTY()) {
				int elementNum = (y - slideUpAnim.target.y)
						/ Settings.MENU_ELEMENT_HEIGHT;
				menuElements.get(elementNum).wasClicked();
			}
		}
	}

	public MenuElement getElement(int i) {
		return menuElements.get(i);
	}

	public void addElement(MenuElement element) {
		menuElements.add(element);
	}

	public void setMouseIsOn(boolean b) {
		if (b == false) {
			slideUpAnim.setMode(Mode.SLIDE_DOWN);
		} else {
			slideUpAnim.setMode(Mode.SLIDE_UP);
		}
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		if (slideUpAnim == null) {
			createSlideUpAnim(posX, posY);
		}

		// draw elements
		Rectangle bounds = slideUpAnim.getDrawingBounds();

		g.setColor(Color.DARK_GRAY);
		int yi = 0;
		for (MenuElement element : menuElements) {
			element.draw(g, bounds.x, bounds.y + yi);
			yi += Settings.MENU_ELEMENT_HEIGHT;
		}

		g.setColor(Color.DARK_GRAY);
		g.fillRect(posX, posY, Settings.MENU_GROUP_WIDTH, Settings.MENU_HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, Settings.MENU_STARTY(), Settings.GAME_FRAME_WIDTH, 3);

		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setColor(Color.LIGHT_GRAY);
		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.MENU_GROUP_WIDTH,
				Settings.MENU_HEIGHT);
		int x = (rect.width - metrics.stringWidth(id)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2);
		g.setFont(font);
		g.drawString(id, posX + x, posY + y + 5);

		g.setColor(Color.BLACK);
		g.drawRect(posX, posY, Settings.MENU_GROUP_WIDTH, Settings.MENU_HEIGHT);
	}

	public void update() {
		if (slideUpAnim != null) {
			slideUpAnim.update();
		}
	}

	public int getAreaStart() {
		if (slideUpAnim == null)
			return Settings.MENU_STARTY();
		else if (Settings.MENU_STARTY() < slideUpAnim.getDrawingBounds().y)
			return Settings.MENU_STARTY();
		return slideUpAnim.getDrawingBounds().y;
	}
}
