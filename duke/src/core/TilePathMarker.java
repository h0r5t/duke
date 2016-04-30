package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class TilePathMarker implements Visual {

	private int x;
	private int y;

	public TilePathMarker(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}

	public void addMarkersToVisualsMap(ViewManager viewMgr) {
		viewMgr.addVisualAt(this, x, y);
	}

	public void removeMarkersFromVisualsMap(ViewManager viewMgr) {
		viewMgr.removeVisualAt(this, x, y);
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		g.setColor(Color.GREEN);

		int ts = Settings.TILE_SIZE;
		g.drawOval(posX + ts / 4, posY + ts / 4, ts / 2, ts / 2);
	}

}
