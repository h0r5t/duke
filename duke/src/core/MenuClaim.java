package core;

public class MenuClaim extends Menu {

	public MenuClaim(MenuManager menuManager) {
		super(menuManager);
		addItem(new MenuItem("select area", "c"));
	}

	@Override
	protected void itemWasClicked(MenuItem i) {
		if (i.getHotkey().equals("c")) {
			Zone2D zone = (Zone2D) getSelectionResult(new SelectorZone2D(menuManager, SelectionType.TYPE_CLAIM));
			if (zone == null)
				return;
			Core.getWorld().getItemManager().claimAllItemsInZone(zone);
			zone.reset();
		}
	}

	@Override
	public String getName() {
		return "claim";
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
