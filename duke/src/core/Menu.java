package core;

public abstract class Menu extends InputAdapter {

	protected MenuManager menuMgr;

	protected

	public Menu(MenuManager menuMgr) {
		this.menuMgr = menuMgr;
	}

	public abstract void update();

}
