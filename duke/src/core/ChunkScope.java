package core;

import java.util.HashMap;

public class ChunkScope {

	private Coords3D rootCoords;
	private HashMap<Coords3D, Chunk> chunkMap;
	private ChunkLoader[] chunkLoaders;
	private int chunkLoaderCounter;

	public ChunkScope() {
		initScopeSize();

		this.chunkMap = new HashMap<>();
		this.rootCoords = calculateRootCoordsBasedOnCamera();
		initChunkLoaders();
		initChunks();
	}

	private void initChunkLoaders() {
		this.chunkLoaders = new ChunkLoader[Settings.CHUNKLOADER_THREAD_AMOUNT];
		this.chunkLoaderCounter = 0;

		for (int i = 0; i < chunkLoaders.length; i++) {
			ChunkLoader cl = new ChunkLoader();
			chunkLoaders[i] = cl;
			new Thread(cl).start();
		}
	}

	public void updateScope() {
		Coords3D newRootCoords = calculateRootCoordsBasedOnCamera();

		if (!newRootCoords.equals(rootCoords)) {
			int xdiff = (newRootCoords.getX() - rootCoords.getX()) / Settings.CHUNK_SIZE;
			int ydiff = (newRootCoords.getY() - rootCoords.getY()) / Settings.CHUNK_SIZE;
			int zdiff = (newRootCoords.getZ() - rootCoords.getZ()) / Settings.CHUNK_DEPTH;

			if (xdiff > 0) {
				// add right chunks
				Coords3D startCoords = scopePosToTilePos(Settings.CHUNK_SCOPE_WIDTH, 0, 0);
				int xchunks = xdiff;
				int ychunks = Settings.CHUNK_SCOPE_HEIGHT;
				int zchunks = Settings.CHUNK_SCOPE_DEPTH;
				addChunkBlock(startCoords, xchunks, ychunks, zchunks);

				// remove left chunks
				startCoords = scopePosToTilePos(0, 0, 0);
				removeChunkBlock(startCoords, xchunks, ychunks, zchunks);
			} else if (xdiff < 0) {
				xdiff = -xdiff;

				// remove right chunks
				Coords3D startCoords = scopePosToTilePos(Settings.CHUNK_SCOPE_WIDTH - xdiff, 0, 0);
				int xchunks = xdiff;
				int ychunks = Settings.CHUNK_SCOPE_HEIGHT;
				int zchunks = Settings.CHUNK_SCOPE_DEPTH;
				removeChunkBlock(startCoords, xchunks, ychunks, zchunks);

				// add left chunks
				startCoords = scopePosToTilePos(-xdiff, 0, 0);
				addChunkBlock(startCoords, xchunks, ychunks, zchunks);
			}

			if (ydiff > 0) {
				// add bottom chunks
				Coords3D startCoords = scopePosToTilePos(0, Settings.CHUNK_SCOPE_HEIGHT, 0);
				int xchunks = Settings.CHUNK_SCOPE_WIDTH;
				int ychunks = ydiff;
				int zchunks = Settings.CHUNK_SCOPE_DEPTH;
				addChunkBlock(startCoords, xchunks, ychunks, zchunks);

				// remove top chunks
				startCoords = scopePosToTilePos(0, 0, 0);
				removeChunkBlock(startCoords, xchunks, ychunks, zchunks);
			} else if (ydiff < 0) {
				ydiff = -ydiff;

				// remove bottom chunks
				Coords3D startCoords = scopePosToTilePos(0, Settings.CHUNK_SCOPE_HEIGHT - ydiff, 0);
				int xchunks = Settings.CHUNK_SCOPE_WIDTH;
				int ychunks = ydiff;
				int zchunks = Settings.CHUNK_SCOPE_DEPTH;
				removeChunkBlock(startCoords, xchunks, ychunks, zchunks);

				// add top chunks
				startCoords = scopePosToTilePos(0, -ydiff, 0);
				addChunkBlock(startCoords, xchunks, ychunks, zchunks);
			}

			if (zdiff > 0) {
				// add below chunks
				Coords3D startCoords = scopePosToTilePos(0, 0, Settings.CHUNK_SCOPE_DEPTH);
				int xchunks = Settings.CHUNK_SCOPE_WIDTH;
				int ychunks = Settings.CHUNK_SCOPE_HEIGHT;
				int zchunks = zdiff;
				addChunkBlock(startCoords, xchunks, ychunks, zchunks);

				// remove above chunks
				startCoords = scopePosToTilePos(0, 0, 0);
				removeChunkBlock(startCoords, xchunks, ychunks, zchunks);
			} else if (zdiff < 0) {
				zdiff = -zdiff;

				// remove below chunks
				Coords3D startCoords = scopePosToTilePos(0, 0, Settings.CHUNK_SCOPE_DEPTH - zdiff);
				int xchunks = Settings.CHUNK_SCOPE_WIDTH;
				int ychunks = Settings.CHUNK_SCOPE_HEIGHT;
				int zchunks = zdiff;
				removeChunkBlock(startCoords, xchunks, ychunks, zchunks);

				// add above chunks
				startCoords = scopePosToTilePos(0, 0, -zdiff);
				addChunkBlock(startCoords, xchunks, ychunks, zchunks);
			}
		}

		rootCoords = newRootCoords;
	}

	private void addChunkBlock(Coords3D startCoordsInTiles, int xchunks, int ychunks, int zchunks) {
		int xstart = startCoordsInTiles.getX();
		int ystart = startCoordsInTiles.getY();
		int zstart = startCoordsInTiles.getZ();

		int width = xchunks * Settings.CHUNK_SIZE;
		int height = ychunks * Settings.CHUNK_SIZE;
		int depth = zchunks * Settings.CHUNK_DEPTH;

		for (int x = xstart; x < xstart + width; x += Settings.CHUNK_SIZE) {
			for (int y = ystart; y < ystart + height; y += Settings.CHUNK_SIZE) {
				for (int z = zstart; z < zstart + depth; z += Settings.CHUNK_DEPTH) {
					Coords3D tileCoords = new Coords3D(x, y, z);
					Chunk chunk = new Chunk(tileCoords);
					chunkMap.put(tileCoords, chunk);

					ChunkLoader chunkLoader = getNextChunkLoader();
					chunkLoader.queueForRendering(chunk);
				}
			}
		}
	}

	private ChunkLoader getNextChunkLoader() {
		ChunkLoader cl = chunkLoaders[chunkLoaderCounter];

		// increment counter
		chunkLoaderCounter++;
		if (chunkLoaderCounter == chunkLoaders.length)
			chunkLoaderCounter = 0;

		return cl;
	}

	private void removeChunkBlock(Coords3D startCoordsInTiles, int xchunks, int ychunks, int zchunks) {
		int xstart = startCoordsInTiles.getX();
		int ystart = startCoordsInTiles.getY();
		int zstart = startCoordsInTiles.getZ();

		int width = xchunks * Settings.CHUNK_SIZE;
		int height = ychunks * Settings.CHUNK_SIZE;
		int depth = zchunks * Settings.CHUNK_DEPTH;

		for (int x = xstart; x < xstart + width; x += Settings.CHUNK_SIZE) {
			for (int y = ystart; y < ystart + height; y += Settings.CHUNK_SIZE) {
				for (int z = zstart; z < zstart + depth; z += Settings.CHUNK_DEPTH) {
					Coords3D tileCoords = new Coords3D(x, y, z);
					chunkMap.remove(tileCoords);
				}
			}
		}
	}

	public Chunk getChunk(int scopeX, int scopeY, int scopeZ) {
		Coords3D tileCoords = scopePosToTilePos(scopeX, scopeY, scopeZ);

		return chunkMap.get(tileCoords);
	}

	public void renderAllChunksSynchronously() {
		for (Chunk c : chunkMap.values()) {
			c.renderWhole();
		}
	}

	private void initChunks() {
		for (int x = 0; x < Settings.CHUNK_SCOPE_WIDTH; x++) {
			for (int y = 0; y < Settings.CHUNK_SCOPE_HEIGHT; y++) {
				for (int z = 0; z < Settings.CHUNK_SCOPE_DEPTH; z++) {
					int tileX = rootCoords.getX() + x * Settings.CHUNK_SIZE;
					int tileY = rootCoords.getY() + y * Settings.CHUNK_SIZE;
					int tileZ = rootCoords.getZ() + z * Settings.CHUNK_DEPTH;
					Coords3D coords = new Coords3D(tileX, tileY, tileZ);
					chunkMap.put(coords, new Chunk(coords));
				}
			}
		}
	}

	private GameCamera getCamera() {
		return Core.getViewManager().getGameCamera();
	}

	private void initScopeSize() {
		Settings.CHUNK_SCOPE_WIDTH = MathUtil
				.roundToClosest((double) Settings.GAME_FRAME_WIDTH / Settings.TILE_SIZE / Settings.CHUNK_SIZE
						+ Settings.CHUNK_SCOPE_OVERLAP_X * 2)
				+ 1;
		Settings.CHUNK_SCOPE_HEIGHT = MathUtil
				.roundToClosest((double) Settings.GAME_FRAME_HEIGHT / Settings.TILE_SIZE / Settings.CHUNK_SIZE
						+ Settings.CHUNK_SCOPE_OVERLAP_Y * 2)
				+ 1;
		Settings.CHUNK_SCOPE_DEPTH = 1 + Settings.CHUNK_SCOPE_OVERLAP_Z * 2;
	}

	private Coords3D scopePosToTilePos(int scopeX, int scopeY, int scopeZ) {
		int tileX = rootCoords.getX() + scopeX * Settings.CHUNK_SIZE;
		int tileY = rootCoords.getY() + scopeY * Settings.CHUNK_SIZE;
		int tileZ = rootCoords.getZ() + scopeZ * Settings.CHUNK_DEPTH;

		return new Coords3D(tileX, tileY, tileZ);
	}

	private Coords3D calculateRootCoordsBasedOnCamera() {
		int startX = MathUtil.roundToClosest((double) getCamera().getCurrentX() / (double) Settings.CHUNK_SIZE)
				- Settings.CHUNK_SCOPE_OVERLAP_X;
		int startY = MathUtil.roundToClosest((double) getCamera().getCurrentY() / (double) Settings.CHUNK_SIZE)
				- Settings.CHUNK_SCOPE_OVERLAP_Y;
		int startZ = getCamera().getCurrentZ() / Settings.CHUNK_DEPTH - Settings.CHUNK_SCOPE_OVERLAP_Z;

		startX *= Settings.CHUNK_SIZE;
		startY *= Settings.CHUNK_SIZE;
		startZ *= Settings.CHUNK_DEPTH;

		return new Coords3D(startX, startY, startZ);
	}

}
