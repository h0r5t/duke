package core;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;
	private ViewManager viewManager;
	private TaskDistributor taskDistributor;
	private UnitManager unitManager;

	private static PathFinder pathFinder;

	private static World world;

	public Core() {
		Chars.load();
		world = WorldGenerator.generateWorld(this);
		initMgrs();
		setupGUI();

		UnitWorker testWorker = new UnitWorker(5, 5, 0);
		unitManager.addUnit(testWorker);

		UnitWorker testWorker2 = new UnitWorker(6, 5, 0);
		unitManager.addUnit(testWorker2);

		UnitWorker testWorker3 = new UnitWorker(4, 5, 0);
		unitManager.addUnit(testWorker3);

		world.setTile(new TileLadderDown(7, 5, 0));
		world.setTile(new TileLand(7, 6, 0));
	}

	private void initMgrs() {
		viewManager = new ViewManager(this);
		inputManager = new InputManager(this);
		taskDistributor = new TaskDistributor(this);
		unitManager = new UnitManager(this);
		pathFinder = new PathFinder();
	}

	private void setupGUI() {
		gameFrame = new GameFrame(new GameWindowAdapter(this));
		gamePanel = new GamePanel(inputManager, viewManager);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	private void loop() {
		inputManager.update();
		taskDistributor.update();
		unitManager.update();

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

	public TaskDistributor getTaskDistributor() {
		return taskDistributor;
	}

	public UnitManager getUnitManager() {
		return unitManager;
	}

	public static PathFinder getPathFinder() {
		return pathFinder;
	}

	public static void main(String[] args) {
		Core core = new Core();
		Thread t = new Thread(core);
		t.start();
	}

}
