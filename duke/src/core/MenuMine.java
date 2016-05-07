package core;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuMine extends Menu {

	private boolean firstClick = false;

	public MenuMine(MenuManager menuMgr) {
		super(menuMgr);
		addLine("select area to mine...");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// TODO
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO
	}

}
