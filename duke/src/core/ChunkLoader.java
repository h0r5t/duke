package core;

import java.util.ArrayList;

public class ChunkLoader implements Runnable {

	private ArrayList<Chunk> chunkList;

	public ChunkLoader() {
		this.chunkList = new ArrayList<>();
	}

	public void queueForRendering(Chunk chunk) {
		chunkList.add(chunk);
	}

	@Override
	public void run() {
		while (true) {
			if (chunkList.size() > 0) {
				Chunk c = chunkList.remove(0);
				c.renderWhole();
			}

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
