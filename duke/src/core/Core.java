package core;

public class Core implements Runnable {

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private InputManager inputManager;

	public Core() {
		setupGUI();
	}

	private void setupGUI() {
		gameFrame = new GameFrame(inputManager, new GameWindowAdapter(this));
		gamePanel = new GamePanel();
		gameFrame.add(gamePanel);
		gameFrame.setVisible(true);
	}

	private void loop() {

	}

	@Override
	public void run() {
		while (true) {

			loop();

			try {
				Thread.sleep(Settings.SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Core core = new Core();
		Thread t = new Thread(core);
		t.start();
	}

}
