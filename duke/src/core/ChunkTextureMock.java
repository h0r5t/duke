package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ChunkTextureMock extends ChunkTexture {

	private BufferedImage blackTexture;

	public ChunkTextureMock() {
		super(0, 0, 0);
		blackTexture = new BufferedImage(Settings.TILE_SIZE * Settings.CHUNK_SIZE,
				Settings.TILE_SIZE * Settings.CHUNK_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) blackTexture.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, blackTexture.getWidth(), blackTexture.getHeight());
		g.dispose();
	}

	@Override
	public void draw(Graphics2D g, int drawX, int drawY) {
		super.draw(g, drawX, drawY);
	}

}
