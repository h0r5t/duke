package core;

public abstract class MenuSelector extends InputAdapter {

	protected MenuManager menuManager;

	public MenuSelector(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public abstract boolean isDone();

	public abstract Object[] getResults();

	public abstract void update();
}
