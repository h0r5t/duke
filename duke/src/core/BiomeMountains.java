package core;

public class BiomeMountains extends Biome {

	@Override
	public Ground getGround() {
		return new GroundRock();
	}
}
