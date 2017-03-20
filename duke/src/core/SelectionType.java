package core;

import java.awt.Color;

public enum SelectionType {

	TYPE_CURSOR,

	TYPE_DESIGNATION,

	TYPE_ZONE;

	public static Color getColorForType(SelectionType t) {
		if (t == SelectionType.TYPE_CURSOR)
			return Color.YELLOW;
		else if (t == SelectionType.TYPE_DESIGNATION)
			return Color.CYAN;
		else if (t == SelectionType.TYPE_ZONE)
			return Color.CYAN;
		return Color.BLACK;
	}

}
