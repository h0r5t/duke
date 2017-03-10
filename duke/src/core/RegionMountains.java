package core;

public class RegionMountains extends Region {

	@Override
	public int getWorldGenLargestFeature() {
		return 100;
	}

	@Override
	public double getWorldGenPersistence() {
		return 0.4;
	}

	@Override
	public double getWorldGenFactorMult() {
		return 0.8;
	}

}
