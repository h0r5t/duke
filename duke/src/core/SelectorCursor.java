package core;

public class SelectorCursor extends Selector {

	private Coords3D result;

	public SelectorCursor(MenuManager menuManager) {
		super(menuManager);
		menuManager.getCursor().setSelectionType(SelectionType.TYPE_DESIGNATION);
	}

	@Override
	public void onEscape() {
		finish();
		menuManager.getCursor().resetSelectionType();
	}

	@Override
	public void onEnter() {
		result = menuManager.getCursor().getCoords3D();
		menuManager.getCursor().resetSelectionType();
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
