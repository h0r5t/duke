package core;

public class RegionPlain extends Region {

	@Override
	public int getWorldGenLargestFeature() {
		return 75;
	}

	@Override
	public double getWorldGenPersistence() {
		// useful: 0.0 - 0.5
		return 0.1;
	}

	@Override
	public double getWorldGenFactorMult() {
		// between 0 and 1
		return 0.2;
	}
}
