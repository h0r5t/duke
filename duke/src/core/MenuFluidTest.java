package core;

import java.awt.Graphics2D;

public class MenuFluidTest extends Menu {

	public MenuFluidTest(MenuManager menuManager) {
		super(menuManager);
	}

	@Override
	public String getName() {
		return "fluid";
	}

	@Override
	public void start() {
		Coords3D c = (Coords3D) getSelectionResult(new SelectorCursor(menuManager));
		Core.getWorld().addFluid(new FluidWater(), c);
	}

	@Override
	public void draw(Graphics2D g) {

	}

}
