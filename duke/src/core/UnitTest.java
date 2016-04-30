package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class UnitTest extends Unit {

	public UnitTest(int id, int x, int y) {
		super(id, x, y);
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		int a = Settings.TILE_SIZE;
		g.setColor(Color.BLACK);
		g.drawOval(posX, posY, a - 1, a - 1);
	}
}
