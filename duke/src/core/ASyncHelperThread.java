package core;

import java.util.ArrayList;

public abstract class ASyncHelperThread implements Runnable {

	protected FutureContainer<Path> p;
	protected Coords3D source;
	protected Coords3D target;

	protected FutureContainer<Coords3D> coords;
	protected ArrayList<Coords3D> targets;

	public ASyncHelperThread(FutureContainer<Path> future, Coords3D from,
			Coords3D to) {
		this.p = future;
		this.source = from;
		this.target = to;
	}

	public ASyncHelperThread(FutureContainer<Coords3D> future, Coords3D from,
			ArrayList<Coords3D> to) {
		this.coords = future;
		this.source = from;
		this.targets = to;
	}
}