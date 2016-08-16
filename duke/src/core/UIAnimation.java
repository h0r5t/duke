package core;

import java.awt.Rectangle;

public abstract class UIAnimation {

	private boolean isDone;
	protected Rectangle source;
	protected Rectangle target;
	protected Rectangle current;

	public UIAnimation(Rectangle source, Rectangle target) {
		this.source = source;
		this.target = target;
		this.current = new Rectangle(source);
	}

	public Rectangle getSource() {
		return source;
	}

	public Rectangle getTarget() {
		return target;
	}

	public Rectangle getDrawingBounds() {
		return current;
	}

	public abstract void update();

	protected void setIsDone(boolean b) {
		this.isDone = b;
	}

	public boolean isDone() {
		return isDone;
	}

}
