package core;

public class ItemStone extends Item {

	public ItemStone() {
		super(GameData.getItemID("item_stone"));
	}

	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public boolean blocksPath() {
		return false;
	}

	@Override
	public int getVolume() {
		return 2;
	}

}
