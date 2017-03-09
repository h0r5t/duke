package core;

public abstract class Biome {

	public Biome() {

	}

	public Ground getGround() {
		return new GroundBlack();
	}

	public static Biome getBiome(int terrainHeight, int moistureLevel) {

		int terrainHeightInPercent = (int) ((double) terrainHeight / (double) Settings.WORLD_DEPTH * 100);

		if (terrainHeightInPercent < 50)
			return new BiomeUnderground();
		else if (terrainHeightInPercent < 60)
			return new BiomeForest();
		else if (terrainHeightInPercent < 70)
			return new BiomeMountains();
		else
			return new BiomeSnowyMountains();

	}
}
