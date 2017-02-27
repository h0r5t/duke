package core;

public abstract class EquipmentWeapon extends Equipment {

	public EquipmentWeapon(int itemID) {
		super(itemID);
	}

	public abstract void useWeapon(Coords3D source, Coords3D target);

	public abstract int getRange();

}
