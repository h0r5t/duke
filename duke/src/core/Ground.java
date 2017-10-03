package core;

import java.awt.Graphics2D;

public abstract class Ground {

	protected int groundID;

	public Ground(int groundID) {
		this.groundID = groundID;
	}

	public void draw(Graphics2D g, int x, int y) {
		TextureStore.getGroundTexture(groundID).draw(g, x, y);
	}

}
