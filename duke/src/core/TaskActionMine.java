package core;

public class TaskActionMine extends TaskAction {

	private Coords3D targetToMine;
	private Unit unit;

	public TaskActionMine(Coords3D targetToMine) {
		super(TaskType.MINING, 100);
		this.targetToMine = targetToMine;
	}

	@Override
	protected void doAction(Unit unit) {
		this.unit = unit;

		Tile unitPos = unit.getTile();
		int unitX = unitPos.getX();
		int unitY = unitPos.getY();
		int tileX = targetToMine.getX();
		int tileY = targetToMine.getY();

		if ((Math.abs(unitX - tileX) <= 1 && Math.abs(unitY - tileY) == 0)
				|| (Math.abs(unitX - tileX) == 0 && Math.abs(unitY - tileY) <= 1)) {
			startTimer();
		} else {
			setStatus(TaskStatus.DONE);
		}
	}

	@Override
	public void callback(int context) {
		Core.getWorld().wasMined(targetToMine);
		setStatus(TaskStatus.DONE);
	}

	@Override
	public boolean isReachableFor(Unit unit) {
		return true;
	}
}
