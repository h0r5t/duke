package core;

public class TaskActionMine extends TaskAction {

	private Tile tileToMine;

	public TaskActionMine(Tile tileToMine) {
		this.tileToMine = tileToMine;
	}

	@Override
	protected void doAction(Unit unit) {
		Tile unitPos = unit.getTile();
		int unitX = unitPos.getX();
		int unitY = unitPos.getY();
		int tileX = tileToMine.getX();
		int tileY = tileToMine.getY();

		if ((Math.abs(unitX - tileX) <= 1 && Math.abs(unitY - tileY) == 0)
				|| (Math.abs(unitX - tileX) == 0 && Math.abs(unitY - tileY) <= 1)) {
			startTimer(1000, 0);
		} else {
			setStatus(TaskStatus.DONE);
		}
	}

	@Override
	public void callback(int context) {
		Core.getWorld().wasMined(tileToMine);
		setStatus(TaskStatus.DONE);
	}

}
