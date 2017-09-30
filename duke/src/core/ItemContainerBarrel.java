package core;

public class ItemContainerBarrel extends ItemContainer {

	public ItemContainerBarrel() {
		super(GameData.getItemID("item_barrel"));
	}

	@Override
	public int getMaxContainerVolume() {
		return 20;
	}

	@Override
	public String getName() {
		return "barrel";
	}

	@Override
	public int getVolume() {
		return 20;
	}

	@Override
	public boolean blocksPath() {
		return false;
	}

}
