package core;

public class UnitMovement {

	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	private double moveDelta;
	private double currentDelta;
	private Coords3D source;
	private Coords3D target;
	private boolean moving;
	private int[] lastDeltas;

	public UnitMovement(Unit u) {
		this.currentDelta = 0;
		this.moveDelta = (double) Settings.TILE_SIZE / (double) (u.getMoveSpeed());
		this.lastDeltas = new int[] { 0, 0 };
	}

	public void startMove(Coords3D source, Coords3D target) {
		moving = true;
		if (source.getX() < target.getX())
			movingRight = true;
		else if (source.getX() > target.getX())
			movingLeft = true;
		else if (source.getY() < target.getY())
			movingDown = true;
		else if (source.getY() > target.getY())
			movingUp = true;
	}

	public int[] getPositionDeltas() {
		return lastDeltas;
	}

	public void updatePositionDeltas() {
		// position needs to be relative to target (target coords is already the
		// new pos of unit)

		if (!moving)
			lastDeltas = new int[] { 0, 0 };

		else {
			int deltaX = 0;
			int deltaY = 0;
			if (currentDelta < Settings.TILE_SIZE) {
				if (movingRight)
					deltaX = (int) (currentDelta - Settings.TILE_SIZE);
				else if (movingLeft)
					deltaX = (int) (Settings.TILE_SIZE - currentDelta);
				else if (movingDown)
					deltaY = (int) (currentDelta - Settings.TILE_SIZE);
				else if (movingUp)
					deltaY = (int) (Settings.TILE_SIZE - currentDelta);
				currentDelta += moveDelta;
			} else {
				deltaX = 0;
				deltaY = 0;
				reset();
			}

			lastDeltas = new int[] { deltaX, deltaY };
		}

	}

	private void reset() {
		movingLeft = false;
		movingRight = false;
		movingUp = false;
		movingDown = false;
		moving = false;
		currentDelta = 0;
	}

	public boolean isMoving() {
		return moving;
	}

}
