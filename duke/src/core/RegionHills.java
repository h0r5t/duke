package core;

public class RegionHills extends Region {

	@Override
	public int getWorldGenLargestFeature() {
		return 100;
	}

	@Override
	public double getWorldGenPersistence() {
		return 0.3;
	}

	@Override
	public double getWorldGenFactorMult() {
		return 0.3;
	}

}
