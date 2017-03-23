package core;

public abstract class Equipment extends Item {

	public Equipment(int itemID) {
		super(itemID);
	}

	@Override
	public int getVolume() {
		return 1;
	}

}
