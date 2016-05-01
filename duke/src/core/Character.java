package core;

import java.awt.Color;

public class Character {

	private char c;
	private Color color;

	public Character(String c, Color color) {
		this.c = c.toCharArray()[0];
		this.color = color;
	}

	public char getChar() {
		return c;
	}

	public Color getColor() {
		return color;
	}

}
