package core;

public abstract class Biome {

	public Biome() {

	}

	public Ground getGround() {
		return new GroundBlack();
	}

	public Tile getSurfaceTile(int x, int y, int z) {
		return new TileRock(x, y, z);
	}

	public static Biome getBiome(int terrainHeight, int moistureLevel) {

		int groundHeight = Settings.WORLD_DEPTH - Settings.OVERWORLD_DEPTH;
		int heightOverGroundPercent = (int) (((double) (terrainHeight - groundHeight)
				/ (double) Settings.OVERWORLD_DEPTH) * 100);

		if (heightOverGroundPercent < 0)
			heightOverGroundPercent = 0;

		// System.out.println(heightOvergGroundPercent);

		if (heightOverGroundPercent < 40)
			return new BiomeForest();
		else if (heightOverGroundPercent < 60)
			return new BiomeMountains();
		else
			return new BiomeSnowyMountains();

	}
}
