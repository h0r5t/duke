package core;

import java.util.ArrayList;
import java.util.Collections;

public class UnitManager {

	private Core core;
	private ArrayList<Unit> units;
	private ArrayList<UnitWorker> workerUnits;
	private ArrayList<Unit> unitsToRemove;

	public UnitManager(Core core) {
		this.core = core;
		units = new ArrayList<Unit>();
		workerUnits = new ArrayList<UnitWorker>();
		unitsToRemove = new ArrayList<>();
	}

	public ArrayList<Unit> getUnitsAt(Tile tile) {
		return getUnitsAt(tile.getX(), tile.getY(), tile.getZ());
	}

	public ArrayList<Unit> getUnitsAt(int x, int y, int z) {
		ArrayList<Unit> un = new ArrayList<>();
		for (int i = 0; i < units.size(); i++) {
			Unit u = units.get(i);
			if (u == null) {
				continue;
			}
			if (u.getX() == x && u.getY() == y && u.getZ() == z)
				un.add(u);
		}
		if (un.size() == 0)
			return null;
		return un;
	}

	public void addUnit(Unit u) {
		units.add(u);
		if (u instanceof UnitWorker)
			workerUnits.add((UnitWorker) u);
	}

	public void removeUnit(Unit unit) {
		unitsToRemove.add(unit);
		if (unit instanceof UnitWorker)
			workerUnits.remove(unit);
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
			u.updateUnit();
		}
		units.removeAll(unitsToRemove);
	}

	public void lowerPrio(UnitWorker u) {
		// moves unit to bottom of list (handle locked-in units.)
		Collections.rotate(workerUnits, -1);
	}
}
