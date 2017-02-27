package core;

import java.awt.Graphics2D;

public class MenuProjectileTest extends Menu {

	public MenuProjectileTest(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public void start() {
		Coords3D source = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
		Coords3D target = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));

		Projectile p = new ProjectileArrow(menuManager.getCore().getWorld(), target);
		menuManager.getCore().getWorld().addProjectileAt(p, source);
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
