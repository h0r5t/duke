package core;

public class BiomeForest extends Biome {

	@Override
	public Ground getGround() {
		return new GroundGrass();
	}

}
