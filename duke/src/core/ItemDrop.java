package core;

import java.util.Random;

public class ItemDrop {

	private Item item;

	public ItemDrop(Item i, int probabilityInPercent) {
		Random r = new Random();
		int randInt = r.nextInt(100);
		if (randInt < probabilityInPercent)
			item = i;
		else
			item = null;
	}

	public Item getItem() {
		return item;
	}
}
