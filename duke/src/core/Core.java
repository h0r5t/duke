package core;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;
	private ViewManager viewManager;
	private TaskDistributor taskDistributor;
	private UnitManager unitManager;
	private MenuManager menuManager;

	private static World world;

	public Core() {
		Chars.load();
		world = WorldGenerator.generateWorld(this);
		initMgrs();
		setupGUI();

		for (int i = 0; i < 20; i++) {
			UnitWorker worker = new UnitWorker(2 + i, 7, 0);
			unitManager.addUnit(worker);
		}

		PathFinder.setReachablePoint(new Coords3D(7, 5, 0));
		world.setTile(new TileLadderDown(7, 5, 0));
	}

	private void initMgrs() {
		viewManager = new ViewManager(this);
		inputManager = new InputManager(this);
		taskDistributor = new TaskDistributor(this);
		unitManager = new UnitManager(this);
		menuManager = new MenuManager(this);
	}

	private void setupGUI() {
		gameFrame = new GameFrame(new GameWindowAdapter(this));
		gamePanel = new GamePanel(inputManager, viewManager);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	private void loop() {
		menuManager.update();
		taskDistributor.update();
		gamePanel.repaint();

		unitManager.update();
		inputManager.update();

	}

	@Override
	public void run() {
		while (true) {
			long time1 = System.currentTimeMillis();

			gamePanel.requestFocus();
			loop();

			long time2 = System.currentTimeMillis() - time1;
			menuManager.setLoopTime((int) time2);

			int sleepTime = (int) (Settings.TICK_TIME - time2);
			if (sleepTime < 0) {
				sleepTime = 0;
			}
			try {
				Thread.sleep(sleepTime);
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

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public static void main(String[] args) {
		Core core = new Core();
		Thread t = new Thread(core);
		t.start();
	}

}
