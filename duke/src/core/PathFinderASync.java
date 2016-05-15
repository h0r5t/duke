package core;

import java.util.ArrayList;

public class PathFinderASync {

	public static FutureContainer<Path> findPath(Coords3D from, Coords3D to) {
		Path returnedPath = new Path(true);
		FutureContainer<Path> futurePath = new FutureContainer<Path>(
				returnedPath);
		new Thread(new ASyncHelperThread(futurePath, from, to) {

			@Override
			public void run() {
				Path newPath = PathFinder.findPath(source, target);
				p.getContained().set(newPath);
				p.setReady(true);
			}
		}).start();
		;

		return futurePath;
	}

	public static FutureContainer<Coords3D> findTargetTileWithShortestPath(
			Coords3D from, ArrayList<Coords3D> to) {
		Coords3D returnedCoords3D = new Coords3D(0, 0, 0);
		FutureContainer<Coords3D> futureCoords3D = new FutureContainer<Coords3D>(
				returnedCoords3D);

		new Thread(new ASyncHelperThread(futureCoords3D, from, to) {

			@Override
			public void run() {
				Coords3D best = PathFinder
						.findTargetTileWithShortestPath(source, targets);
				coords.setContained(best);
				coords.setReady(true);
			}
		}).start();
		;

		return futureCoords3D;
	}

}
