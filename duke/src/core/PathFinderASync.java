package core;

public class PathFinderASync {

	public static void findPath(UsesPathFinderASync client, Coords3D from,
			Coords3D to) {
		new Thread(new PathFinderThread(client, from, to)).start();
	}

}
