package core;

public class MenuInspect extends Menu {

	public MenuInspect(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	protected void itemWasClicked(MenuItem i) {

	}

	@Override
	public String getName() {
		return "inspect";
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
		// TEST TEST TEST
		getMenuItems().clear();
		Coords3D location = cursor.getCoords3D();

		for (Item i : Core.getWorld().getItemsAt(location)) {
			if (i instanceof ItemContainerBarrel) {
				MultiMap<Integer, Item> map = ((ItemContainerBarrel) i).getContainedItems();
				for (Integer id : map.keySet()) {
					addItem(new MenuItem(id + "", map.get(id) + ""));
				}
			}
		}
	}

}
