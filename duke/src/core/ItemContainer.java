package core;

public abstract class ItemContainer extends Item {

	private MultiMap<Class<? extends Item>, Item> containedItems;
	private int containedVolume;

	public ItemContainer(int itemID) {
		super(itemID);
		this.containedItems = new MultiMap<>();
		this.containedVolume = 0;
	}

	public boolean addItem(Item i) {
		if (containedVolume + i.getVolume() > getMaxContainerVolume())
			return false;
		this.containedItems.putOne(i.getClass(), i);
		containedVolume += i.getVolume();
		return true;
	}

	public boolean hasItemOfType(Class<? extends Item> i) {
		return containedItems.hasAtLeastOneOf(i);
	}

	public boolean isEmpty() {
		return containedVolume == 0;
	}

	public Item removeItemOfType(Class<? extends Item> i) {
		Item returnItem = containedItems.removeAny(i);
		if (returnItem != null)
			containedVolume -= returnItem.getVolume();
		return returnItem;
	}

	public abstract int getMaxContainerVolume();

}