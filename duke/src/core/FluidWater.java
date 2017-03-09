package core;

import java.awt.Color;

public class FluidWater extends Fluid {

	public FluidWater() {
	}

	@Override
	protected Color getFluidColor() {
		int alpha = 150 + 10 * fluidDensity;
		if (alpha > 255)
			alpha = 255;
		return new Color(50, 100, 180, alpha);
	}

	@Override
	protected Color getTextColor() {
		return new Color(255, 255, 255, 100);
	}

	@Override
	public Fluid getNewInstance() {
		return new FluidWater();
	}

}
