package core;

public class BiomeUnderground extends Biome {

	public BiomeUnderground(WorldGenerator wGen) {
		super(wGen);
	}

	@Override
	public Ground getGround() {
		return new GroundRock();
	}

}
