package core;

import java.awt.Graphics2D;

public abstract class Unit {

	protected int x;
	protected int y;
	protected int z;
	protected int unitID;
	protected int moveCooldown;
	protected Character myChar;
	protected Task currentTask;
	private Item itemInHands;
	private Inventory inventory;
	private double health;
	private UnitMovement unitMovement;

	public Unit(int id, int x, int y, int z, int moveSpeed) {
		this.unitID = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.health = 100;
		this.moveCooldown = 50 - moveSpeed;
		this.inventory = new Inventory();
		this.unitMovement = new UnitMovement(this);
		getChar();
	}

	public void damage(int damage) {
		this.health -= damage;
	}

	public Item getItemInHands() {
		return itemInHands;
	}

	public void setItemInHands(Item itemInHands) {
		this.itemInHands = itemInHands;
	}

	public void removeItemFromHands() {
		this.itemInHands = null;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean isMoving() {
		return unitMovement.isMoving();
	}

	public void updateUnit() {
		if (currentTask != null) {
			if (currentTask.getStatus() == TaskStatus.DONE) {
				currentTask = null;
			} else if (currentTask.getStatus() == TaskStatus.FAILED) {
				currentTask = null;
				System.out.println("task failed.");
			} else
				currentTask.update(this);
		}

		if (this.health <= 0) {
			Core.getWorld().removeUnit(this);
		}
		unitMovement.updatePositionDeltas();
		update();
	}

	public abstract void onDeath();

	public abstract void update();

	protected void getChar() {
		myChar = GameData.getRandomUnitCharacter(unitID);
	}

	public int getUnitID() {
		return unitID;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getMoveSpeed() {
		return moveCooldown;
	}

	public boolean hasTask() {
		return currentTask != null;
	}

	public int getEstimatedDurationToDoTask(Task t) {
		int sum = 0;
		if (currentTask != null) {
			sum += currentTask.getEstimatedTimeNeeded(this);
		}
		sum += t.getEstimatedTimeNeeded(this);
		return sum;
	}

	public Task getTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public void moveTo(int xpos, int ypos, int zpos) {
		Coords3D source = new Coords3D(x, y, z);
		Coords3D target = new Coords3D(xpos, ypos, zpos);

		this.x = xpos;
		this.y = ypos;
		this.z = zpos;

		if (itemInHands != null) {
			itemInHands.moveTo(new Coords3D(xpos, ypos, zpos));
		}

		unitMovement.startMove(source, target);
	}

	public int[] getCurrentMovementDeltas() {
		return unitMovement.getPositionDeltas();
	}

	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		int[] movementDeltas = unitMovement.getPositionDeltas();
		posX += movementDeltas[0];
		posY += movementDeltas[1];

		TextureStore.getUnitTexture(unitID, myChar).draw(g, posX, posY, darkerLevel);

		// draw health bar
		// g.setColor(Color.GREEN);
		// g.fillRect(posX + 3, posY - 1, (int) ((health / 100) *
		// (Settings.TILE_SIZE - 6)), 3);
		// g.setColor(Color.BLACK);
		// g.drawRect(posX + 3, posY - 1, Settings.TILE_SIZE - 6, 3);
	}

	public Tile getTile() {
		return Core.getWorld().getTile(x, y, z);
	}

	public Coords3D getCoords() {
		return new Coords3D(x, y, z);
	}
}
