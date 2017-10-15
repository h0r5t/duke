package core;

public class Chunk implements IEventSubscriber {

	private int tileX;
	private int tileY;
	private int tileZ;
	private ChunkTexture[] chunkTextures;
	private boolean rendered;

	public Chunk(Coords3D tileCoords) {
		this.tileX = tileCoords.getX();
		this.tileY = tileCoords.getY();
		this.tileZ = tileCoords.getZ();
		chunkTextures = new ChunkTexture[Settings.CHUNK_DEPTH];
		rendered = false;
	}

	// takes long, ChunkLoader calls this
	public void renderWhole() {
		for (int i = 0; i < chunkTextures.length; i++) {
			ChunkTexture t = new ChunkTexture(tileX, tileY, tileZ + i);
			t.renderWhole();
			chunkTextures[i] = t;
		}
		rendered = true;
	}

	public int getX() {
		return tileX;
	}

	public int getY() {
		return tileY;
	}

	public int getZ() {
		return tileZ;
	}

	public ChunkTexture getChunkTexture(int z) {
		if (rendered)
			return chunkTextures[z];
		else
			return ChunkTexture.getTextureMock();
	}

	@Override
	public void eventOcurred(AEvent e) {

	}

}
