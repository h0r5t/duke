package core;

public class FutureContainer<K> {

	private K contained;
	private boolean ready;

	public FutureContainer(K k) {
		this.contained = k;
	}

	public void setContained(K contained) {
		this.contained = contained;
	}

	public K getContained() {
		return contained;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

}
