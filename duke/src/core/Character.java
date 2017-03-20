package core;

import java.awt.Color;

public class Character {

	private String c;
	private boolean fill;
	private Color color;
	private Color bgColor;
	private int fontSize;

	public Character(String c, Color color, int fontSize) {
		if (c.equals("FILL"))
			fill = true;
		this.c = c;
		this.color = color;
		this.fontSize = fontSize;
	}

	public Character(String c, Color color, int fontSize, Color bgColor) {
		if (c.equals("FILL"))
			fill = true;
		this.c = c;
		this.color = color;
		this.fontSize = fontSize;
		this.bgColor = bgColor;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public boolean isFill() {
		return fill;
	}

	public String getChar() {
		return c;
	}

	public Color getColor() {
		return color;
	}

	public int getFontSize() {
		return fontSize;
	}

}
