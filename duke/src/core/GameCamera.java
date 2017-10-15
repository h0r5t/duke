package core;

public class GameCamera {

	private int currentX;
	private int currentY;
	private int currentZ;

	public GameCamera(int currentX, int currentY, int currentZ) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.currentZ = currentZ;
	}

	public int getCurrentX() {
		return currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public int getCurrentZ() {
		return currentZ;
	}

	public void moveRight() {
		currentX++;
	}

	public void moveLeft() {
		currentX--;
	}

	public void moveDown() {
		currentY++;
	}

	public void moveUp() {
		currentY--;
	}

	public void moveZ(int delta) {
		currentZ += delta;
		if (currentZ < 0) {
			currentZ = 0;
		}

		else if (currentZ > Settings.WORLD_DEPTH - 1) {
			currentZ = Settings.WORLD_DEPTH - 1;
		}
	}

	// public int translateScreenPosToTilePosX(int x) {
	// return (x + screenShiftX) / Settings.TILE_SIZE;
	// }
	//
	// public int translateScreenPosToTilePosY(int y) {
	// return (y + screenShiftY) / Settings.TILE_SIZE;
	// }
	//
	// public Tile getTileFromScreenPos(int x, int y) {
	// return Core.getWorld().getTile(translateScreenPosToTilePosX(x),
	// translateScreenPosToTilePosY(y), currentZ);
	// }

	// public void shiftScreenX(int delta) {
	// if (screenShiftX + delta < 0 || (screenShiftX + delta) / Settings.TILE_SIZE
	// + Settings.SCREEN_WIDTH_IN_TILES() > Settings.WORLD_WIDTH + 1)
	// return;
	// screenShiftX += delta;
	// }
	//
	// public void shiftScreenY(int delta) {
	// if (screenShiftY + delta < 0 || (screenShiftY + delta) / Settings.TILE_SIZE
	// + Settings.SCREEN_HEIGHT_IN_TILES() > Settings.WORLD_HEIGHT + 2)
	// return;
	// screenShiftY += delta;
	// }

}
