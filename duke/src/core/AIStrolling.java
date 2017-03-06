package core;

public class AIStrolling extends AI {

	public AIStrolling(Unit unit) {
		super(unit);
	}

	@Override
	public void doLogic() {
		if (unit.hasTask()) {
			return;
		}

		int xr = (int) (Math.random() * 10 - 5);
		int yr = (int) (Math.random() * 10 - 5);
		Coords3D coords = new Coords3D(unit.getX() + xr, unit.getY() + yr, unit.getZ());
		moveTo(coords);
		rest(2000);
	}

}
