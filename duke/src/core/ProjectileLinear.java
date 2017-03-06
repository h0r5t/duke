package core;

import java.util.ArrayList;

public abstract class ProjectileLinear extends Projectile {

	protected Coords3D source;
	protected Coords3D target;
	protected int speed;
	protected double movementDeltaX;
	protected double movementDeltaY;
	private boolean initialized;
	private int range;

	public ProjectileLinear(World world, Coords3D target, int speed, int range) {
		super(world);
		this.target = target;
		this.speed = speed;
		this.range = range;
	}

	public abstract void onUnitHit(Unit u);

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

		double baseStepSize = speed;
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

		double stepX = movementDeltaX / 5;
		double stepY = movementDeltaY / 5;

		for (int i = 0; i < 5; i++) {
			doMovement(stepX, stepY);
			if (tilePosition == null)
				break;
		}
	}

	private void doMovement(double deltaX, double deltaY) {
		microPositionX += deltaX;
		microPositionY += deltaY;

		int tileDeltaX = 0;
		int tileDeltaY = 0;

		while (true) {
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

			if (tileDeltaX != 0 || tileDeltaY != 0) {
				Tile newTile = world.getTile(tilePosition.getX() + tileDeltaX, tilePosition.getY() + tileDeltaY,
						tilePosition.getZ());
				attachToTile(newTile);
				tileDeltaX = 0;
				tileDeltaY = 0;

				ArrayList<Unit> units = world.getUnitsAt(newTile.getCoords3D());
				if (units != null) {
					for (Unit u : units) {
						onUnitHit(u);
					}
					tilePosition.removeProjectile(this);
					Core.getWorld().removeProjectile(this);
					tilePosition = null;
					return;
				}

				double currentRange = newTile.getCoords3D().getDistance2D(source);
				if (currentRange >= range) {
					tilePosition.removeProjectile(this);
					Core.getWorld().removeProjectile(this);
					tilePosition = null;
					return;
				}

				if (newTile.collides()) {
					tilePosition.removeProjectile(this);
					Core.getWorld().removeProjectile(this);
					tilePosition = null;
					return;
				}

			} else
				break;

		}

	}

}
