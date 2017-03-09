package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Fluid {

	protected Coords3D coords;
	protected int fluidDensity;
	protected boolean isStatic;
	protected Font font;
	protected ColorStore colorStore;
	protected static final int DEFAULT_DENSITY = 9;

	public Fluid() {
		this.fluidDensity = DEFAULT_DENSITY;
		this.isStatic = false;
		this.coords = null;
		this.font = new Font("Arial", Font.BOLD, 20);
		this.colorStore = new ColorStore(getFluidColor(), getTextColor());
	}

	protected abstract Color getFluidColor();

	protected abstract Color getTextColor();

	public abstract Fluid getNewInstance();

	private void wakeUpNeighbours() {
		if (coords.getLeft().getTile().hasFluid()) {
			coords.getLeft().getTile().getFluid().setStatic(false);
		}
		if (coords.getTop().getTile().hasFluid()) {
			coords.getTop().getTile().getFluid().setStatic(false);
		}
		if (coords.getRight().getTile().hasFluid()) {
			coords.getRight().getTile().getFluid().setStatic(false);
		}
		if (coords.getBottom().getTile().hasFluid()) {
			coords.getBottom().getTile().getFluid().setStatic(false);
		}
	}

	public void setStatic(boolean b) {
		this.isStatic = b;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void activate() {
		if (coords.getTile().hasFluid()) {
			coords.getTile().getFluid().addFluidDensity(9);
		} else
			coords.getTile().setFluid(this);

		wakeUpNeighbours();
	}

	public void remove() {
		coords.getTile().setFluid(null);
	}

	public Coords3D getCoords() {
		return coords;
	}

	public void setCoords(Coords3D coords) {
		this.coords = coords;
	}

	public void fallDown() {
		// swap with tile below, null if no fluid below
		wakeUpNeighbours();

		Tile tileBelow = coords.getBelow().getTile();
		if (!tileBelow.hasFluid()) {
			this.coords.getTile().setFluid(null);
			tileBelow.setFluid(this);
		} else {
			Fluid fluidBelow = tileBelow.getFluid();
			if (fluidBelow.getFluidDensity() + fluidDensity <= DEFAULT_DENSITY) {
				this.coords.getTile().setFluid(null);
				addFluidDensity(fluidBelow.getFluidDensity());
				tileBelow.setFluid(this);
			} else {
				int rest = fluidBelow.getFluidDensity() + fluidDensity - DEFAULT_DENSITY;
				setFluidDensity(rest);
				tileBelow.getFluid().setFluidDensity(DEFAULT_DENSITY);
			}
			tileBelow.getFluid().setStatic(false);
		}

	}

	public int getFluidDensity() {
		return fluidDensity;
	}

	public void setFluidDensity(int fluidDensity) {
		this.fluidDensity = fluidDensity;
	}

	public void addFluidDensity(int add) {
		this.fluidDensity += add;
		if (fluidDensity > 1)
			isStatic = false;
		else if (fluidDensity < 0)
			fluidDensity = 0;
		if (fluidDensity > DEFAULT_DENSITY)
			fluidDensity = DEFAULT_DENSITY;
		wakeUpNeighbours();
	}

	public void draw(Graphics2D g, int posX, int posY, int darkerLevel) {
		colorStore = new ColorStore(getFluidColor(), getTextColor());
		g.setColor(colorStore.getBackgroundColor(darkerLevel));
		g.fillRect(posX, posY, Settings.TILE_SIZE, Settings.TILE_SIZE);

		g.setColor(colorStore.getForegroundColor(darkerLevel));
		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(0, 0, Settings.TILE_SIZE, Settings.TILE_SIZE);
		String text = getFluidDensity() + "";
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, posX + x, posY + y);
	}

}
