package core;

import java.awt.Color;

public class Character {

	private char c;
	private boolean fill;
	private Color color;
	private int fontSize;

	public Character(String c, Color color, int fontSize) {
		if (c.equals("FILL"))
			fill = true;
		this.c = c.toCharArray()[0];
		this.color = color;
		this.fontSize = fontSize;
	}

	public boolean isFill() {
		return fill;
	}

	public char getChar() {
		return c;
	}

	public Color getColor() {
		return color;
	}

	public int getFontSize() {
		return fontSize;
	}

}
