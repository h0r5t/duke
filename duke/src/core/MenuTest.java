package core;

public class MenuTest extends Menu {

	public MenuTest(MenuManager menuManager) {
		super(menuManager);
		addItem(new MenuItem("projectile test", "p"));
		addItem(new MenuItem("fluid test", "f"));
	}

	@Override
	protected void itemWasClicked(MenuItem i) {
		if (i.getText().equals("projectile test")) {
			Coords3D source = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
			Coords3D target = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
			ProjectileArrow arrow = new ProjectileArrow(Core.getWorld(), target);
			Core.getWorld().addProjectileAt(arrow, source);
		}

		if (i.getText().equals("fluid test")) {
			Coords3D coords = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
			Core.getWorld().addFluid(new FluidWater(), coords);
		}
	}

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

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
