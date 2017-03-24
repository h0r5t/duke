package core;

import java.util.ArrayList;

public abstract class Building extends Tile {

	private ArrayList<ItemInput> itemInput;

	public Building(int x, int y, int z) {
		super(x, y, z);
		itemInput = new ArrayList<ItemInput>();
	}

	protected void addItemInput(ItemInput input) {
		itemInput.add(input);
	}

	public ArrayList<ItemInput> getItemInputs() {
		return itemInput;
	}

	public void wasBuilt() {
		Core.getWorld().setTile(this);
	}

}
