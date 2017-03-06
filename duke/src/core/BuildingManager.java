package core;

import java.util.ArrayList;

public class BuildingManager {

	private ArrayList<Building> buildings;

	public BuildingManager() {
		this.buildings = new ArrayList<>();
	}

	public void addBuilding(Building b) {
		buildings.add(b);
	}

	public void removeBuilding(Building b) {
		buildings.remove(b);
	}

}
