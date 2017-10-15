package core;

public class EventTileExposureChanged extends AEvent {

	private Tile tile;

	public EventTileExposureChanged(Tile tile) {
		this.tile = tile;
	}

	public Tile getTile() {
		return tile;
	}

}
