package core;

import java.util.ArrayList;

public class Zone2D {

	private int startX;
	private int startY;
	private int z;
	private ArrayList<Coords3D> locations;
	private int width;
	private int height;
	private SelectionType zoneType;

	public Zone2D(int startX, int startY, int z, SelectionType zoneType) {
		this.startX = startX;
		this.startY = startY;
		this.z = z;
		locations = new ArrayList<Coords3D>();
		setEnd(startX, startY);
		this.zoneType = zoneType;
	}

	public int getZ() {
		return z;
	}

	public void setEnd(int endX, int endY) {
		ArrayList<Coords3D> oldLocations = locations;
		locations = new ArrayList<Coords3D>();
		if (endX < startX)
			endX = startX;
		if (endY < startY)
			endY = startY;

		Coords3D coord;
		for (int i = startX; i < endX + 1; i++) {
			for (int o = startY; o < endY + 1; o++) {
				coord = new Coords3D(i, o, z);
				Tile tile = coord.getTile();
				tile.select(this);
				locations.add(coord);
			}
		}

		for (Coords3D c : oldLocations) {
			if (!locations.contains(c)) {
				Core.getWorld().getTile(c).deselect(this);
			}
		}

		this.width = endX - startX;
		this.height = endY - startY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ArrayList<Coords3D> getCoords() {
		return locations;
	}

	public void reset() {
		for (Coords3D c : locations) {
			c.getTile().deselect(this);
		}
	}

	public void markAllMineables() {
		for (Coords3D c : locations) {
			if (c.getTile().canBeMined())
				c.getTile().select(this);
		}
	}

	public SelectionType getSelectionType() {
		return zoneType;
	}

	public void removeNonMineables() {
		for (Coords3D c : locations) {
			if (!c.getTile().canBeMined())
				c.getTile().deselect(this);
		}
	}

	public int getX() {
		return startX;
	}

	public int getY() {
		return startY;
	}

}
