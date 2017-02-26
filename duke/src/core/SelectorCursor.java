package core;

public class SelectorCursor extends MenuSelector {

	private Coords3D result;

	public SelectorCursor(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public void onEscape() {
		finish();
	}

	@Override
	public void onEnter() {
		result = menuManager.getCursor().getCoords3D();
		finish();
	}

	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void update() {

	}

}
