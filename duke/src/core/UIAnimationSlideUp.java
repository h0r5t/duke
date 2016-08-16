package core;

import java.awt.Rectangle;

public class UIAnimationSlideUp extends UIAnimation {

	public enum Mode {
		SLIDE_UP, SLIDE_DOWN;
	}

	private Mode mode;

	public UIAnimationSlideUp(Rectangle source, Rectangle target) {
		super(source, target);
		this.mode = Mode.SLIDE_DOWN;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public void update() {
		setIsDone(false);
		if (mode == Mode.SLIDE_DOWN) {
			if (!current.equals(source))
				current.y += 10;
		} else if (mode == Mode.SLIDE_UP) {
			if (!current.equals(target)) {
				current.y -= 10;
				setIsDone(false);
			}

		}
		if (current.equals(target)) {
			setIsDone(true);
		}
	}

}
