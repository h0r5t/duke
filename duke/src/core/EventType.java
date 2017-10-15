package core;

public enum EventType {

	TILE_CHANGED, TILE_EXPOSURE_CHANGED;

	public static EventType getEventType(AEvent e) {
		if (e instanceof EventTileChanged)
			return TILE_CHANGED;
		if (e instanceof EventTileExposureChanged)
			return TILE_EXPOSURE_CHANGED;

		return null;
	}

}
