package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuText {

	private String hotkey;
	private String name;
	private boolean selected;

	public MenuText(String hotkey, String name) {
		this.hotkey = hotkey;
		this.name = name;
	}

	public MenuText(String name) {
		this.hotkey = null;
		this.name = name;
	}

	public String getHotkey() {
		return hotkey;
	}

	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}

	public void draw(Graphics2D g, int x, int y) {
		if (hotkey != null) {
			g.setColor(Color.YELLOW);
			g.drawString(hotkey, x, y);
		}

		x += 50;

		g.setColor(Color.LIGHT_GRAY);
		if (selected)
			g.setColor(Color.YELLOW);
		g.drawString(name, x, y);
	}

}
