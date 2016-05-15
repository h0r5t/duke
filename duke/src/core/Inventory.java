package core;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> items;

	public Inventory() {
		items = new ArrayList<>();
	}

	public void addItem(Item i) {
		items.add(i);
	}

	public void removeItem(Item i) {
		items.remove(i);
	}

}
