package core;

import java.util.ArrayList;

public class UnitManager {

	private Core core;
	private ArrayList<Unit> units;

	public UnitManager(Core core) {
		this.core = core;
		units = new ArrayList<Unit>();
	}

	public void addUnit(Unit u) {
		units.add(u);
	}

	public void update() {
		for (Unit u : units) {
			u.update();
			if (u.getCurrentTask() == null) {
				Task t = core.getTaskManager().getNextOpenTask();
				if (t != null)
					u.setCurrentTask(t);
			}
		}
	}

}
