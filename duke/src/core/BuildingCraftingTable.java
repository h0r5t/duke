package core;

public class BuildingCraftingTable extends Building {

	public BuildingCraftingTable(Coords3D coords) {
		super(coords.getX(), coords.getY(), coords.getZ());

		addItemInput(new ItemInput(GameData.getItemID("item_wood"), 3));
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
