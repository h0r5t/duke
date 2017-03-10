package core;

public class BuildingCraftingTable extends Building {

	public BuildingCraftingTable(Coords3D coords) {
		super(GameData.getTileID("tile_building_crafting_table"), coords.getX(), coords.getY(), coords.getZ());

		addItemInput(new ItemInput(GameData.getItemID("item_wood"), 3));
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
