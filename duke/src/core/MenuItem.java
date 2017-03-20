package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuItem {

	private String text;
	private String hotkey;
	private Menu link;

	public MenuItem(String text, String hotkey) {
		this.text = text;
		this.hotkey = hotkey;
	}

	public MenuItem(String text, String hotkey, Menu link) {
		this.text = text;
		this.hotkey = hotkey;
		this.link = link;
	}

	public boolean isLink() {
		return this.link != null;
	}

	public Menu getLink() {
		return link;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHotkey() {
		return hotkey;
	}

	public void draw(Graphics2D g, int x, int y) {
		g.setColor(Color.GREEN);
		g.drawString(hotkey.toLowerCase() + ":", x, y);
		g.setColor(Color.WHITE);
		g.drawString(text, x + 30, y);
	}

}
