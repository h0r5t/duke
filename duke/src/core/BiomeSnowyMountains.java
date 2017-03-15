package core;

public class BiomeSnowyMountains extends Biome {

	public BiomeSnowyMountains(WorldGenerator wGen) {
		super(wGen);
	}

	@Override
	public Ground getGround() {
		return new GroundSnow();
	}

}
