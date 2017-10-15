package core;

public class ChunkManager {

	private ChunkScope chunkScope;

	public ChunkManager() {
		chunkScope = new ChunkScope();
		chunkScope.renderAllChunksSynchronously();
	}

	public ChunkScope getChunkScope() {
		return chunkScope;
	}

	public void update() {
		chunkScope.updateScope();
	}

}
