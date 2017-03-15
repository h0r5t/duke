package core;

import java.util.HashMap;
import java.util.Random;

public abstract class Biome {

	private WorldGenerator wGen;
	private HashMap<String, Double> probabilityMap;
	private int probSum = 0;
	private Random random;

	public Biome(WorldGenerator wGen) {
		this.wGen = wGen;
		this.probabilityMap = new HashMap<>();
		this.random = new Random();
		addProbability(getDefaultSurfaceTile(), 100);
	}

	protected void addProbability(String tileName, double p) {
		probabilityMap.put(tileName, p);
		probSum += p;
	}

	public Tile getDetailTile(int x, int y, int z) {
		String s = getRandomItem();
		if (s.equals(null))
			return null;
		return GameData.getTileInstanceFromId(s, x, y, z);
	}

	private String getRandomItem() {
		double r = random.nextDouble() * probSum;
		double cumulative = 0;
		for (String s : probabilityMap.keySet()) {
			cumulative += probabilityMap.get(s);
			if (r <= cumulative) {
				return s;
			}
		}

		return null;
	}

	public Ground getGround() {
		return new GroundBlack();
	}

	public String getDefaultSurfaceTile() {
		return "TileRock";
	}

	protected int getSurfaceLevel(int x, int y) {
		return wGen.getSurfaceLevel(x, y);
	}

	public static Biome getBiome(WorldGenerator wGen, int terrainHeight, int moistureLevel) {

		int groundHeight = Settings.WORLD_DEPTH - Settings.OVERWORLD_DEPTH;
		int heightOverGroundPercent = (int) (((double) (terrainHeight - groundHeight)
				/ (double) Settings.OVERWORLD_DEPTH) * 100);

		if (heightOverGroundPercent < 0)
			heightOverGroundPercent = 0;

		if (heightOverGroundPercent < 40)
			return new BiomeForest(wGen);
		else if (heightOverGroundPercent < 60)
			return new BiomeMountains(wGen);
		else
			return new BiomeSnowyMountains(wGen);

	}
}
