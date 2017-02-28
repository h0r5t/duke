package core;

public class MobGoblin extends Mob {

	public MobGoblin(int x, int y, int z) {
		super(GameData.getUnitID("unit_goblin"), x, y, z, 10, AIType.TYPE_STROLLING);
	}

	@Override
	public void updateMob() {

	}

	@Override
	public boolean attacksOnSight() {
		return false;
	}

	@Override
	public void onDeath() {

	}

}
