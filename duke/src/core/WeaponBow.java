package core;

public class WeaponBow extends EquipmentWeapon {

	public WeaponBow(int itemID) {
		super(GameData.getItemID("item_bow"));
	}

	@Override
	public String getName() {
		return "bow";
	}

	@Override
	public int getRange() {
		return 30;
	}

	@Override
	public void useWeapon(Coords3D source, Coords3D target) {

	}

	@Override
	public boolean collides() {
		return false;
	}

}
