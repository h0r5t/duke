package core;

import java.awt.Color;

public class ColorUtils {

	public static Color makeColorDarker(Color color, int darkerLevel) {
		if (darkerLevel == 0)
			return color;
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int reduceBy = 0;

		int min = 0;
		if (r > 0) {
			if (g > 0) {
				if (b > 0)
					min = Math.min(r, Math.min(g, b));
				else
					min = Math.min(r, g);
			} else if (b > 0)
				min = Math.min(r, b);
			else
				min = r;
		} else {
			if (g > 0) {
				if (b > 0)
					min = Math.min(g, b);
				else
					min = g;
			} else if (b > 0)
				min = b;
		}

		reduceBy = (min) / Settings.DRAW_DARKER_LEVELS_AMOUNT;
		if (reduceBy < 0)
			reduceBy = 0;
		r -= reduceBy * darkerLevel;
		g -= reduceBy * darkerLevel;
		b -= reduceBy * darkerLevel;
		if (r < 0)
			r = 0;
		if (g < 0)
			g = 0;
		if (b < 0)
			b = 0;

		Color newColor = new Color(r, g, b);
		return newColor;
	}

	public static Color changeColor(Color color, int range, int alpha) {
		if (range == 0)
			return color;

		int delta = (int) (Math.random() * range);

		int r = color.getRed() + range / 2;
		int g = color.getGreen() + range / 2;
		int b = color.getBlue() + range / 2;
		int reduceBy = 0;

		int min = 0;
		if (r > 0) {
			if (g > 0) {
				if (b > 0)
					min = Math.min(r, Math.min(g, b));
				else
					min = Math.min(r, g);
			} else if (b > 0)
				min = Math.min(r, b);
			else
				min = r;
		} else {
			if (g > 0) {
				if (b > 0)
					min = Math.min(g, b);
				else
					min = g;
			} else if (b > 0)
				min = b;
		}

		reduceBy = delta;
		if (reduceBy < 0)
			reduceBy = 0;
		r -= reduceBy;
		g -= reduceBy;
		b -= reduceBy;
		if (r < 0)
			r = 0;
		if (g < 0)
			g = 0;
		if (b < 0)
			b = 0;

		if (alpha == -1)
			return new Color(r, g, b);
		return new Color(r, g, b, alpha);
	}

}
