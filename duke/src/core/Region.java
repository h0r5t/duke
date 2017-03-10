package core;

public abstract class Region {

	public Region() {

	}

	public abstract int getWorldGenLargestFeature();

	public abstract double getWorldGenPersistence();

	public abstract double getWorldGenFactorMult();

	public int[][] getHeightMap(int width, int height) {
		int fMult = (int) ((double) Settings.WORLD_DEPTH * getWorldGenFactorMult());
		int factorAdd = -fMult / 2 + (Settings.WORLD_DEPTH - Settings.OVERWORLD_DEPTH);
		if (factorAdd < 0)
			factorAdd = 0;

		Settings.CAMERA_START_Z = Settings.OVERWORLD_DEPTH;

		SimplexNoise noise = new SimplexNoise(getWorldGenLargestFeature(), getWorldGenPersistence(),
				(int) (Math.random() * 5000));
		int[][] values = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double val = noise.getNoise(x, y);

				val = (val + 1.0) / 2.0;

				if (val < 0)
					val = 0;

				val = val * fMult + factorAdd;

				int z = (int) val;
				values[x][y] = z;
			}
		}

		return values;
	}

	public int[][] getMoistureMap(int width, int height) {
		SimplexNoise noise = new SimplexNoise(30, 0.7, (int) (Math.random() * 5000));
		int[][] values = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double val = noise.getNoise(x, y);
				val = (val + 1.0) / 2.0;
				values[x][y] = (int) val;
			}
		}

		return values;
	}

}
