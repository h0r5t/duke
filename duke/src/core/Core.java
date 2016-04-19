package core;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;
	private ViewManager viewManager;

	private World world;

	public Core() {
		initMgrs();
		setupGUI();
		world = World.generateWorld();
	}

	private void initMgrs() {
		viewManager = new ViewManager(this);
		inputManager = new InputManager(viewManager);
		TileManager.load();
	}

	private void setupGUI() {
		gameFrame = new GameFrame(new GameWindowAdapter(this));
		gamePanel = new GamePanel(inputManager, viewManager);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	private void loop() {
		inputManager.update();
		gamePanel.repaint();
	}

	@Override
	public void run() {
		while (true) {

			gamePanel.requestFocus();
			loop();

			try {
				Thread.sleep(Settings.SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public World getWorld() {
		return world;
	}

	public static void main(String[] args) {
		Core core = new Core();
		Thread t = new Thread(core);
		t.start();
	}

}
