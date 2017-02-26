package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class SelectorToolTipOneLayer extends MenuSelectorDrawable {

	private String[] options;
	private int selectedOption;
	private String result;

	public SelectorToolTipOneLayer(MenuManager menuManager, String[] options) {
		super(menuManager);
		this.options = options;
		this.selectedOption = 0;
	}

	public SelectorToolTipOneLayer(MenuManager menuManager, Inventory inventory) {
		super(menuManager);
		this.options = new String[inventory.getItems().size()];
		int c = 0;
		for (Item i : inventory.getItems()) {
			options[c] = i.getName();
			c++;
		}
		this.selectedOption = 0;
	}

	@Override
	public void onEscape() {
		result = null;
		finish();
	}

	@Override
	public void onEnter() {
		result = options[selectedOption];
		finish();
	}

	@Override
	public Object getResult() {
		return result;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			if (selectedOption > 0)
				selectedOption--;
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			if (selectedOption < options.length - 1)
				selectedOption++;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
			onEnter();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			onEscape();
		}
	}

	@Override
	public void draw(Graphics g) {
		if (options == null)
			return;

		int xpos = 120;
		int width = 200;

		int height = 20 * options.length;
		int ypos = Settings.GAME_FRAME_HEIGHT - height - 50;

		Font font = new Font("Arial", Font.PLAIN, 14);
		g.setFont(font);

		g.setColor(Color.WHITE);
		g.fillRect(xpos, ypos, width, height);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xpos, ypos + 20 * selectedOption, width, 20);

		g.setColor(Color.BLACK);
		g.drawRect(xpos, ypos, width, height);
		int yshift = 15;
		for (int i = 0; i < options.length; i++) {
			String o = options[i];

			g.drawString(o, xpos + 5, ypos + yshift);
			yshift += 20;
		}
	}

	@Override
	public void update() {

	}

}
