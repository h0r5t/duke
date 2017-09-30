package core;

public abstract class ItemContainer extends Item {

	private MultiMap<Integer, Item> containedItems;
	private int containedVolume;

	public ItemContainer(int itemID) {
		super(itemID);
		this.containedItems = new MultiMap<>();
		this.containedVolume = 0;
	}

	public void reserveSpaceFor(Item item) {
		this.containedVolume += item.getVolume();
	}

	public boolean canAddItem(Item i) {
		if (containedVolume + i.getVolume() > getMaxContainerVolume())
			return false;
		if (i instanceof ItemContainer)
			return false;
		return true;
	}

	public boolean addItem(Item i) {
		if (!canAddItem(i))
			return false;
		this.containedItems.putOne(i.getItemID(), i);
		containedVolume += i.getVolume();
		return true;
	}

	public boolean hasItemOfType(Item i) {
		return containedItems.hasAtLeastOneOf(i.getItemID());
	}

	public boolean hasItemOfType(Integer i) {
		return containedItems.hasAtLeastOneOf(i);
	}

	public boolean isEmpty() {
		return containedVolume == 0;
	}

	public Item removeItemOfType(Integer i) {
		Item returnItem = containedItems.removeAny(i);
		if (returnItem != null)
			containedVolume -= returnItem.getVolume();
		return returnItem;
	}

	public abstract int getMaxContainerVolume();

	public MultiMap<Integer, Item> getContainedItems() {
		return containedItems;
	}

}