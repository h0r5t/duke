package core;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindowAdapter extends WindowAdapter {
	private Core core;

	public GameWindowAdapter(Core core) {
		this.core = core;
	}

	public void windowClosing(WindowEvent windowEvent) {
		System.exit(0);
	}
}
