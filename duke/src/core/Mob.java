package core;

public abstract class Mob extends Unit {

	protected AI myAI;

	public Mob(int id, int x, int y, int z, int moveSpeed, AIType aiType) {
		super(id, x, y, z, moveSpeed);

		if (aiType == AIType.TYPE_STROLLING)
			myAI = new AIStrolling(this);

		// new Thread(myAI).start();
	}

	@Override
	public void update() {
		updateMob();
	}

	public abstract void updateMob();

	public abstract boolean attacksOnSight();

}
