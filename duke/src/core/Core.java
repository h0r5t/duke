package core;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;
	private ViewManager viewManager;
	private TaskDistributor taskDistributor;
	private UnitManager unitManager;
	private MenuManager menuManager;
	private static LogisticsManager logisticsManager;
	private FluidManager fluidManager;

	private static World world;

	public Core() {
		modifySysOut();
		GameData.load();
		TextureStore.load();

		WorldGenerator worldGen = new WorldGenerator();
		world = worldGen.generateWorld(this);

		initMgrs();
		setupGUI();

		spawnUnits(worldGen.getEmbarkArea());
	}

	private void initMgrs() {
		viewManager = new ViewManager(this);
		taskDistributor = new TaskDistributor(this);
		unitManager = new UnitManager(this);
		inputManager = new InputManager(this);
		menuManager = new MenuManager(this);
		logisticsManager = new LogisticsManager();
		fluidManager = new FluidManager(this);
		new Thread(inputManager).start();
	}

	private void setupGUI() {
		gameFrame = new GameFrame(new GameWindowAdapter(this));
		gamePanel = new GamePanel(inputManager, viewManager);
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	private void spawnUnits(ArrayList<Coords3D> embarkArea) {
		for (int i = 0; i < 1; i++) {
			Coords3D c = embarkArea.get(i);
			TileGrass t = new TileGrass(c.getX(), c.getY(), c.getZ());
			world.setTile(t);

			UnitWorker worker = new UnitWorker(c.getX(), c.getY(), c.getZ());
			unitManager.addUnit(worker);
		}

		for (int i = 10; i < 13; i++) {
			Coords3D c = embarkArea.get(i);
			TileGrass t = new TileGrass(c.getX(), c.getY(), c.getZ());
			world.setTile(t);

			ItemContainerBarrel barrel = new ItemContainerBarrel();
			Core.getWorld().addItem(barrel, c);
			logisticsManager.addItemContainer(barrel);
		}
	}

	private void loop() {
		world.update(this);
		menuManager.update();
		taskDistributor.update();
		unitManager.update();
		inputManager.update();
		fluidManager.udpate();

		gamePanel.repaint();
	}

	@Override
	public void run() {
		while (true) {

			gamePanel.requestFocus();
			loop();

			try {
				Thread.sleep(Settings.TICK_TIME);
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

	public static LogisticsManager getLogisticsManager() {
		return logisticsManager;
	}

	public FluidManager getFluidManager() {
		return fluidManager;
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
