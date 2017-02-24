package core;

public class MenuMine extends Menu {

	public MenuMine(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public void start() {
		Zone2D zone = (Zone2D) getSelectionResults(new Zone2DSelector(menuManager, SelectionType.TYPE_DESIGNATION))[0];
		System.err.println(zone.getWidth());
	}

	// private void createMiningTaskGroup() {
	// TaskGroupMining taskGroup = new TaskGroupMining(areaSelection);
	// menuManager.getCore().getTaskDistributor().addTaskGroup(taskGroup);
	// new Thread(taskGroup).start();
	// }

}
