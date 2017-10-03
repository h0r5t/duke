package core;

public class BiomeForest extends Biome {

	public BiomeForest(WorldGenerator wGen) {
		super(wGen);
		addProbability(new TileGrass(0, 0, 0), 5);
		addProbability(new TileTrunkOnGround(0, 0, 0), 1); // will get expanded to full tree later
		addProbability(new TileBush(0, 0, 0), 3);
		addProbability(new TileStone(0, 0, 0), 2);
		addProbability(new TileMushroom(0, 0, 0), 1);
	}

	@Override
	public Ground getGround() {
		return new GroundGrass();
	}

	@Override
	public Tile getDefaultSurfaceTile() {
		return new TileGround(0, 0, 0);
	}
}
