package core;

public class ItemInput {

	private int itemID;
	private int amount;

	public ItemInput(int itemID, int amount) {
		this.itemID = itemID;
		this.amount = amount;
	}

	public int getItemID() {
		return itemID;
	}

	public int getAmount() {
		return amount;
	}

}
