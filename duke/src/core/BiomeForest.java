package core;

public class BiomeForest extends Biome {

	public BiomeForest(WorldGenerator wGen) {
		super(wGen);
		addProbability("TileTrunkOnGround", 1); // will get expanded to full tree later
		addProbability("TileBush", 3);
		addProbability("TileStone", 2);
		addProbability("TileMushroom", 1);
	}

	@Override
	public Ground getGround() {
		return new GroundGrass();
	}

	@Override
	public String getDefaultSurfaceTile() {
		return "TileLand";
	}
}
