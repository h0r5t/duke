package core;

public class BiomeSnowyMountains extends Biome {

	@Override
	public Ground getGround() {
		return new GroundSnow();
	}

}
