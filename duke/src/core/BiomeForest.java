package core;

public class BiomeForest extends Biome {

	@Override
	public Ground getGround() {
		return new GroundGrass();
	}

	@Override
	public Tile getSurfaceTile(int x, int y, int z) {
		return new TileLand(x, y, z);
	}

}
