package core;

import java.awt.Color;

public class Character {

	private char c;
	private Color color;
	private int fontSize;

	public Character(String c, Color color, int fontSize) {
		this.c = c.toCharArray()[0];
		this.color = color;
		this.fontSize = fontSize;
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
