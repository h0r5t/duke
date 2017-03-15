package core;

import java.awt.Graphics2D;

public class MenuMine extends Menu {

	public MenuMine(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public void start() {
		Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_DESIGNATION));
		if (zone == null)
			return;
		zone.reset();
		zone.markAllMineables();
		createMiningTaskGroup(zone);
	}

	private void createMiningTaskGroup(Zone2D zone) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				TaskMiningWhole t = new TaskMiningWhole(zone);
				menuManager.getCore().getTaskDistributor().addTask(t);
			}
		}).start();

	}

	@Override
	public String getName() {
		return "mining";
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
