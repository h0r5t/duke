package core;

public class ItemInput {

	private Class<? extends Item> itemClass;
	private int amount;

	public ItemInput(Class<? extends Item> itemClass, int amount) {
		this.itemClass = itemClass;
		this.amount = amount;
	}

	public Class<? extends Item> getItemType() {
		return itemClass;
	}

	public int getAmount() {
		return amount;
	}

}
