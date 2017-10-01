package core;

public class BuildingCraftingTable extends Building {

	public BuildingCraftingTable(Coords3D coords) {
		super(GameData.getTileID("building_crafting_table"), coords.getX(), coords.getY(), coords.getZ());

		addItemInput(new ItemInput(ItemWood.class, 3));
	}

	@Override
	public boolean isSolid() {
		return false;
	}

}
