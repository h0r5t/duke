package core;

import java.util.ArrayList;
import java.util.Random;

public class FluidManager {

	private ArrayList<Fluid> fluids;
	private ArrayList<Fluid> fluidsToAdd;
	private int counter = 0;
	private Random randomGen;
	private int calculations = 0;

	public FluidManager(Core core) {
		this.fluids = new ArrayList<>();
		this.fluidsToAdd = new ArrayList<>();
		this.randomGen = new Random();
	}

	public void addFluid(Fluid f) {
		fluids.add(f);
	}

	public int getCalculations() {
		return calculations;
	}

	public void udpate() {
		if (counter == 100) {
			counter = 0;
			updateFluids();
			fluids.addAll(fluidsToAdd);
			fluidsToAdd.clear();
		}
		counter++;
	}

	private void updateFluids() {
		calculations = 0;
		for (Fluid f : fluids) {
			if (f.isStatic())
				continue;

			calculations++;

			boolean wasMoved = false;

			Coords3D coord1 = f.getCoords();
			Tile tileBelow = coord1.getBelow().getTile();

			if (coord1.getTile() instanceof TileAir && !tileBelow.isSolid()) {
				if (tileBelow.hasFluid()) {
					if (!(tileBelow.getFluid().getFluidDensity() >= Fluid.DEFAULT_DENSITY)) {
						f.fallDown();
						wasMoved = true;
					}
				} else {
					f.fallDown();
					wasMoved = true;
				}

			}

			else if (f.getFluidDensity() >= 1) {

				ArrayList<Coords3D> availableNeighbours = new ArrayList<Coords3D>();

				if (!coord1.getTop().getTile().isSolid())
					availableNeighbours.add(coord1.getTop());
				if (!coord1.getRight().getTile().isSolid())
					availableNeighbours.add(coord1.getRight());
				if (!coord1.getBottom().getTile().isSolid())
					availableNeighbours.add(coord1.getBottom());
				if (!coord1.getLeft().getTile().isSolid())
					availableNeighbours.add(coord1.getLeft());

				while (availableNeighbours.size() > 0) {
					int n = randomGen.nextInt(availableNeighbours.size());
					Coords3D coord2 = availableNeighbours.remove(n);

					if (coord2.getTile().hasFluid() == false) {
						int delta = f.getFluidDensity();
						if (delta <= 1) {
							continue;
						} else {
							int d = (int) (delta / 2.0);
							f.addFluidDensity(-d);
							coord2.getTile().setFluid(f.getNewInstance());
							coord2.getTile().getFluid().setFluidDensity(d);
							wasMoved = true;
							fluidsToAdd.add(coord2.getTile().getFluid());
						}

					}

					else if (coord2.getTile().hasFluid()) {
						int delta = f.getFluidDensity() - coord2.getTile().getFluid().getFluidDensity();
						if (delta <= 1) {
							continue;

						} else {
							int d = (int) (delta / 2.0);
							f.addFluidDensity(-d);
							coord2.getTile().getFluid().addFluidDensity(d);
							wasMoved = true;
							// fluidsToAdd.add(coord2.getTile().getFluid());
						}

					}

				}

			}
			if (wasMoved == false) {
				f.setStatic(true);
			}

		}
	}

}
