package core;

public class BiomeForest extends Biome {

	public BiomeForest(WorldGenerator wGen) {
		super(wGen);
		addProbability("TileTrunk", 1); // will get expanded to full tree later
		addProbability("TileBush", 10);
		addProbability("TileStone", 5);
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
