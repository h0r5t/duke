package core;

import java.awt.Graphics2D;

public class DrawingOperation {

	private Drawable d;
	int drawX;
	int drawY;

	public DrawingOperation(Drawable d, int drawX, int drawY) {
		this.d = d;
		this.drawX = drawX;
		this.drawY = drawY;
	}

	public void draw(Graphics2D g) {
		d.draw(g, drawX, drawY);
	}

}
