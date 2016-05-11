package core;

import java.util.ArrayList;
import java.util.Collections;

public class UnitManager {

	private Core core;
	private ArrayList<Unit> units;
	private ArrayList<UnitWorker> workerUnits;

	public UnitManager(Core core) {
		this.core = core;
		units = new ArrayList<Unit>();
		workerUnits = new ArrayList<UnitWorker>();
	}

	public Unit getUnitAt(Tile tile) {
		return getUnitAt(tile.getX(), tile.getY(), tile.getZ());
	}

	public Unit getUnitAt(int x, int y, int z) {
		for (int i = 0; i < units.size(); i++) {
			Unit u = units.get(i);
			if (u == null) {
				continue;
			}
			if (u.getX() == x && u.getY() == y && u.getZ() == z)
				return u;
		}
		return null;
	}

	public void addUnit(Unit u) {
		units.add(u);
		if (u instanceof UnitWorker)
			workerUnits.add((UnitWorker) u);
	}

	public ArrayList<UnitWorker> getAvailableWorkerUnits() {
		ArrayList<UnitWorker> available = new ArrayList<UnitWorker>();
		for (UnitWorker u : workerUnits) {
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

	public void lowerPrio(UnitWorker u) {
		// moves unit to bottom of list (handle locked-in units.)
		Collections.rotate(workerUnits, -1);
	}

}
