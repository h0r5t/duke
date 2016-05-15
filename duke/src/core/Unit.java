package core;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Unit implements Visual {

	protected int x;
	protected int y;
	protected int z;
	protected int unitID;
	protected int moveSpeed;
	protected Character myChar;
	protected Task currentTask;
	protected TaskChain activeTaskChain;
	private Inventory inventory;

	public Unit(int id, int x, int y, int z, int moveSpeed) {
		this.unitID = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.moveSpeed = moveSpeed;
		inventory = new Inventory();
		getChar();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void update() {
		if (currentTask != null) {
			currentTask.update(this);
			if (currentTask.status == TaskStatus.DONE) {
				currentTask = null;
				if (activeTaskChain != null)
					activeTaskChain.update(this);
			}
		}

	}

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
		return moveSpeed;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public boolean hasTask() {
		return currentTask != null;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public void setActiveTaskChain(TaskChain chain) {
		activeTaskChain = chain;
	}

	public void moveTo(int xpos, int ypos, int zpos) {
		this.x = xpos;
		this.y = ypos;
		this.z = zpos;
	}

	@Override
	public void draw(Graphics2D g, int posX, int posY) {
		Font font = new Font("Arial", Font.BOLD, Settings.CHAR_FONT_SIZE);
		g.setColor(myChar.getColor());

		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE,
				Settings.TILE_SIZE);
		String text = myChar.getChar() + "";
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, posX + x, posY + y);
	}

	public Tile getTile() {
		return Core.getWorld().getTile(x, y, z);
	}

	public Coords3D getCoords() {
		return new Coords3D(x, y, z);
	}
}
