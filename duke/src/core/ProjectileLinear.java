package core;

public abstract class ProjectileLinear extends Projectile {

	protected Coords3D source;
	protected Coords3D target;
	protected int speed;
	protected double movementDeltaX;
	protected double movementDeltaY;
	private boolean initialized;

	public ProjectileLinear(World world, Coords3D target, int speed) {
		super(world);
		this.target = target;
		this.speed = speed;
	}

	private void init() {
		int directionX = 0;
		int directionY = 0;
		source = tilePosition.getCoords3D();
		if (source.getX() - target.getX() > 0)
			directionX = -1;
		else if (source.getX() - target.getX() < 0)
			directionX = 1;
		if (source.getY() - target.getY() > 0)
			directionY = -1;
		else if (source.getY() - target.getY() < 0)
			directionY = 1;

		double distanceX = Math.abs(source.getX() - target.getX());
		double distanceY = Math.abs(source.getY() - target.getY());

		double baseStepSize = 20;
		double stepSizeX = baseStepSize;
		double stepSizeY = baseStepSize;
		if (distanceX > distanceY) {
			stepSizeY = ((distanceY / distanceX) * baseStepSize);
		} else if (distanceX < distanceY) {
			stepSizeX = ((distanceX / distanceY) * baseStepSize);
		}

		movementDeltaX = stepSizeX * directionX;
		movementDeltaY = stepSizeY * directionY;
	}

	@Override
	public void update() {
		if (!initialized) {
			init();
			initialized = true;
		}

		doMovement(movementDeltaX, movementDeltaY);

	}

	private void doMovement(double deltaX, double deltaY) {
		microPositionX += deltaX;
		microPositionY += deltaY;

		int tileDeltaX = 0;
		int tileDeltaY = 0;

		if (microPositionX > Settings.TILE_SIZE) {
			tileDeltaX = 1;
			microPositionX -= Settings.TILE_SIZE;
		} else if (microPositionX < 0) {
			tileDeltaX = -1;
			microPositionX += Settings.TILE_SIZE;
		}
		if (microPositionY > Settings.TILE_SIZE) {
			tileDeltaY = 1;
			microPositionY -= Settings.TILE_SIZE;
		} else if (microPositionY < 0) {
			tileDeltaY = -1;
			microPositionY += Settings.TILE_SIZE;
		}

		attachToTile(
				world.getTile(tilePosition.getX() + tileDeltaX, tilePosition.getY() + tileDeltaY, tilePosition.getZ()));

		if (tilePosition.getCoords3D().equals(target)) {
			tilePosition.removeProjectile(this);
			Core.getWorld().removeProjectile(this);
		}
	}

}
