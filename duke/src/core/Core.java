package core;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;
	private ViewManager viewManager;
	private PathFinder pathFinder;

	private static World world;

	public Core() {
		Chars.load();
		world = World.generateWorld();
		initMgrs();
		setupGUI();

		UnitWorker unitTest = new UnitWorker(0, 5, 5);
		world.addUnit(unitTest);
	}

	private void initMgrs() {
		viewManager = new ViewManager();
		inputManager = new InputManager(this);
		pathFinder = new PathFinder(this);
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

	public static World getWorld() {
		return world;
	}

	public ViewManager getViewManager() {
		return viewManager;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public static void main(String[] args) {
		Core core = new Core();
		Thread t = new Thread(core);
		t.start();
	}

}
