package core;

import java.util.ArrayList;

public class UnitManager {

	private Core core;
	private ArrayList<Unit> units;

	public UnitManager(Core core) {
		this.core = core;
		units = new ArrayList<Unit>();
	}

	public Unit getUnitAt(Tile tile) {
		return getUnitAt(tile.getX(), tile.getY(), tile.getZ());
	}

	public Unit getUnitAt(int x, int y, int z) {
		for (Unit u : units) {
			if (u.getX() == x && u.getY() == y && u.getZ() == z)
				return u;
		}
		return null;
	}

	public void addUnit(Unit u) {
		units.add(u);
	}

	public ArrayList<Unit> getAvailableUnits() {
		ArrayList<Unit> available = new ArrayList<Unit>();
		for (Unit u : units) {
			if (!u.hasTask()) {
				available.add(u);
			}
		}
		return available;
	}

	public Unit getNextAvailableUnit() {
		for (Unit u : units) {
			if (!u.hasTask()) {
				return u;
			}
		}

		return null;
	}

	public void update() {
		for (Unit u : units) {
			u.update();
		}
	}

}
