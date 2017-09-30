package core;

public class ProjectileArrow extends ProjectileLinear {

	public ProjectileArrow(World world, Coords3D target) {
		super(world, target, 5, 30);
	}

	@Override
	public void onUnitHit(Unit u) {
		u.damage(25);
	}

}
