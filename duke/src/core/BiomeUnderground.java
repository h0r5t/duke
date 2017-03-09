package core;

public class BiomeUnderground extends Biome {

	@Override
	public Ground getGround() {
		return new GroundRock();
	}

}
