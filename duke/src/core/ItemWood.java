package core;

public class ItemWood extends Item {

	public ItemWood() {
		super(GameData.getItemID("item_wood"));
	}

	@Override
	public String getName() {
		return "wood";
	}

	@Override
	public boolean blocksPath() {
		return false;
	}

	@Override
	public int getVolume() {
		return 4;
	}

}
