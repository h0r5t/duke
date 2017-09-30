package core;

public class MenuDesignations extends Menu {

	private MenuItem itemDig;
	private MenuItem itemFellTree;

	public MenuDesignations(MenuManager menuManager) {
		super(menuManager);

		itemDig = new MenuItem("dig", "r");
		addItem(itemDig);

		itemFellTree = new MenuItem("fell a tree", "t");
		addItem(itemFellTree);
	}

	@Override
	protected void itemWasClicked(MenuItem i) {
		if (i == itemDig) {
			Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_DESIGNATION));
			if (zone == null)
				return;
			zone.reset();
			zone.markAllMineables();
			createMiningTaskGroup(zone);

		} else if (i == itemFellTree) {
			Coords3D c = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
			if (c == null)
				return;
			if (c.getTile() instanceof TileTrunk) {
				TaskChopTree t = new TaskChopTree(c);
				menuManager.getCore().getTaskDistributor().addTask(t);
			}
		}
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
		return "designations";
	}

	@Override
	public void start() {

	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCursorMoved(Cursor cursor) {
		// TODO Auto-generated method stub

	}

}
