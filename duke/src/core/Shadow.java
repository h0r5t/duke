package core;

import java.awt.Graphics2D;

public class Shadow implements Drawable {

	private String shadowDirection;
	private int drawX;
	private int drawY;

	public Shadow(Direction d, int drawX, int drawY) {
		if (d == Direction.BOTTOM) {
			this.shadowDirection = "shadow_bottom";
		} else if (d == Direction.LEFT) {
			this.shadowDirection = "shadow_left";
		} else if (d == Direction.TOP) {
			this.shadowDirection = "shadow_top";
		} else if (d == Direction.RIGHT) {
			this.shadowDirection = "shadow_right";
		} else if (d == Direction.BOTTOM_LEFT) {
			this.shadowDirection = "shadow_bottom_left";
		} else if (d == Direction.BOTTOM_RIGHT) {
			this.shadowDirection = "shadow_bottom_right";
		} else if (d == Direction.TOP_LEFT) {
			this.shadowDirection = "shadow_top_left";
		} else if (d == Direction.TOP_RIGHT) {
			this.shadowDirection = "shadow_top_right";
		}
		this.drawX = drawX;
		this.drawY = drawY;
	}

	public int getDrawX() {
		return drawX;
	}

	public int getDrawY() {
		return drawY;
	}

	public void draw(Graphics2D g, int drawX, int drawY) {
		TextureStore.getShadowTexture(shadowDirection).draw(g, drawX, drawY);
	}

}
