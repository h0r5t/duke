package core;

public class PathFinderThread implements Runnable {

	private UsesPathFinderASync client;
	private Coords3D from;
	private Coords3D to;

	public PathFinderThread(UsesPathFinderASync client, Coords3D from,
			Coords3D to) {
		this.client = client;
	}

	@Override
	public void run() {
		Path p = PathFinder.findPath(from, to);
		client.pathCallback(p);
	}

}
