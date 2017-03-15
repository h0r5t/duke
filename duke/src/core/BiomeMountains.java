package core;

public class BiomeMountains extends Biome {

	public BiomeMountains(WorldGenerator wGen) {
		super(wGen);
	}

	@Override
	public Ground getGround() {
		return new GroundRock();
	}
}
