package core;

import java.io.OutputStream;
import java.io.PrintStream;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;
	private ViewManager viewManager;
	private TaskDistributor taskDistributor;
	private UnitManager unitManager;
	private MenuManager menuManager;
	private LogisticsManager logisticsManager;

	private static World world;

	public Core() {
		modifySysOut();
		GameData.load();
		world = WorldGenerator.generateWorld(this);
		initMgrs();
		setupGUI();

		for (int i = 0; i < 3; i++) {
			world.setTile(new TileLand(2 + i, 7, 0));
			UnitWorker worker = new UnitWorker(2 + i, 7, 0);
			unitManager.addUnit(worker);
		}

		PathFinder.setReachablePoint(new Coords3D(7, 5, 0));
		world.setTile(new TileLadderDown(7, 5, 0));
	}

	private void initMgrs() {
		viewManager = new ViewManager(this);
		taskDistributor = new TaskDistributor(this);
		unitManager = new UnitManager(this);
		menuManager = new MenuManager(this);
		logisticsManager = new LogisticsManager(this);
		inputManager = new InputManager(this);
		new Thread(inputManager).start();
	}

	private void setupGUI() {
		gameFrame = new GameFrame(new GameWindowAdapter(this));
		gamePanel = new GamePanel(inputManager, viewManager);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	private void loop() {
		world.update(this);
		menuManager.update();
		taskDistributor.update();
		gamePanel.repaint();
		unitManager.update();
	}

	@Override
	public void run() {
		while (true) {
			long time1 = System.currentTimeMillis();

			gamePanel.requestFocus();
			loop();

			long time2 = System.currentTimeMillis() - time1;

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

	public LogisticsManager getLogisticsManager() {
		return logisticsManager;
	}

	private void modifySysOut() {
		String className = "pathfinder.GraphSearch_Astar";
		final PrintStream originalOut = System.out;
		PrintStream filterStream = new PrintStream(new OutputStream() {
			public void write(int b) {
				StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
				if (!stackTraceElements[11].getClassName().equals(className)) {
					originalOut.write(b);
				}
			}
		});
		System.setOut(filterStream);

	}

	public static void main(String[] args) {
		Core core = new Core();
		Thread t = new Thread(core);
		t.start();
	}

}
