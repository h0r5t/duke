package core;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class ViewManager {

	private Core core;
	private GameFrame gameFrame;
	private BufferStrategy bufferStrategy;
	private GameCamera gameCamera;
	private ChunkManager chunkManager;

	private Cursor cursor;

	public ViewManager(Core core) {
		this.core = core;
		this.gameFrame = new GameFrame(new GameWindowAdapter(core));
		this.gameCamera = new GameCamera(0, 0, Settings.CAMERA_START_Z);

		gameFrame.createBufferStrategy(2);
		bufferStrategy = gameFrame.getBufferStrategy();

		// this.vImage = gameFrame.createVolatileImage(Settings.GAME_FRAME_WIDTH * 2,
		// Settings.GAME_FRAME_HEIGHT * 2);
	}

	public void render() {
		long a = System.currentTimeMillis();
		Graphics2D screenGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		// screenGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		long b = System.currentTimeMillis();
		renderWorld(screenGraphics);
		b = System.currentTimeMillis() - a;

		core.getMenuManager().render(screenGraphics);
		screenGraphics.dispose();

		bufferStrategy.show();

		// Toolkit.getDefaultToolkit().sync();

		a = System.currentTimeMillis() - a;
		if (a > 20)
			System.out.println(b + " " + (a - b));
	}

	private void renderWorld(Graphics2D g) {
		int chunkShiftX = gameCamera.getCurrentX() % Settings.CHUNK_SIZE;
		int chunkShiftY = gameCamera.getCurrentY() % Settings.CHUNK_SIZE;
		int chunkShiftZ = gameCamera.getCurrentZ() % Settings.CHUNK_DEPTH;

		if (chunkShiftX < Settings.CHUNK_SIZE / 2)
			chunkShiftX += Settings.CHUNK_SIZE;
		if (chunkShiftY < Settings.CHUNK_SIZE / 2)
			chunkShiftY += Settings.CHUNK_SIZE;

		for (int x = 0; x < Settings.CHUNK_SCOPE_WIDTH; x++) {
			for (int y = 0; y < Settings.CHUNK_SCOPE_HEIGHT; y++) {
				int chunkStartX = x * Settings.CHUNK_SIZE;
				int chunkStartY = y * Settings.CHUNK_SIZE;

				int drawX = (chunkStartX - chunkShiftX) * Settings.TILE_SIZE;
				int drawY = (chunkStartY - chunkShiftY) * Settings.TILE_SIZE;

				Chunk c = chunkManager.getChunkScope().getChunk(x, y, 1);
				ChunkTexture chunkTex = c.getChunkTexture(chunkShiftZ);

				chunkTex.draw(g, drawX, drawY);
			}
		}

	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public void setVisible(boolean b) {
		gameFrame.setVisible(true);
	}

	public void setInputManager(InputManager inputManager) {
		gameFrame.setInputManager(inputManager);
		this.cursor = core.getInputManager().getCursor();
	}

	public void setChunkManager(ChunkManager cm) {
		chunkManager = cm;
	}
}
