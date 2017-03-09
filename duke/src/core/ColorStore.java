package core;

import java.awt.Color;

public class ColorStore {

	private Color[] backgroundColors;
	private Color[] foregroundColors;

	public ColorStore(Color backgroundBaseColor, Color foregroundBaseColor) {

		int alphaBg = backgroundBaseColor.getAlpha();
		int alphaFg = foregroundBaseColor.getAlpha();

		backgroundColors = new Color[Settings.DRAW_DARKER_LEVELS_AMOUNT];
		foregroundColors = new Color[Settings.DRAW_DARKER_LEVELS_AMOUNT];

		for (int i = 0; i < Settings.DRAW_DARKER_LEVELS_AMOUNT; i++) {
			backgroundColors[i] = ColorUtils.makeColorDarker(backgroundBaseColor, i, alphaBg);
			foregroundColors[i] = ColorUtils.makeColorDarker(foregroundBaseColor, i, alphaFg);
		}
	}

	public Color getBackgroundColor(int level) {
		return backgroundColors[level];
	}

	public Color getForegroundColor(int level) {
		return foregroundColors[level];
	}

}
