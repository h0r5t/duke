package core;

public class EventTileChanged extends AEvent {

	private Tile oldTile;
	private Tile newTile;

	public EventTileChanged(Tile oldTile, Tile newTile) {
		this.oldTile = oldTile;
		this.newTile = newTile;
	}

	public Tile getOldTile() {
		return oldTile;
	}

	public Tile getNewTile() {
		return newTile;
	}

}
