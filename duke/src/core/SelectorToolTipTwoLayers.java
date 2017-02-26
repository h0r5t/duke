package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectorToolTipTwoLayers extends MenuSelectorDrawable {

	private ArrayList<SelectorToolTipQuery> queries;
	private int selectedQuery;

	public SelectorToolTipTwoLayers(MenuManager menuManager, ArrayList<SelectorToolTipQuery> queries) {
		super(menuManager);
		this.queries = queries;
		this.selectedQuery = 0;
	}

	@Override
	public void onEscape() {
		queries = null;
		finish();
	}

	@Override
	public void onEnter() {
		finish();
	}

	@Override
	public Object getResult() {
		if (queries == null)
			return null;
		HashMap<String, String> resultsMap = new HashMap<>();
		for (SelectorToolTipQuery q : queries) {
			resultsMap.put(q.getName(), q.getSelectedOption());
		}
		return resultsMap;
	}

	@Override
	public void update() {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			if (selectedQuery > 0)
				selectedQuery--;
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			if (selectedQuery < queries.size() - 1)
				selectedQuery++;
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			queries.get(selectedQuery).nextOption();
		} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			queries.get(selectedQuery).previousOption();
		}

		else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
			onEnter();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			onEscape();
		}
	}

	@Override
	public void draw(Graphics g) {
		if (queries == null)
			return;
		int xpos = 120;
		int width = 200;

		int height = 20 * queries.size();
		int ypos = Settings.GAME_FRAME_HEIGHT - height - 50;

		Font font = new Font("Arial", Font.PLAIN, 14);
		g.setFont(font);

		g.setColor(Color.WHITE);
		g.fillRect(xpos, ypos, width, height);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xpos, ypos + 20 * selectedQuery, width, 20);

		g.setColor(Color.BLACK);
		g.drawRect(xpos, ypos, width, height);
		int yshift = 15;
		for (int i = 0; i < queries.size(); i++) {
			SelectorToolTipQuery q = queries.get(i);

			g.drawString(q.getName() + ":", xpos + 5, ypos + yshift);
			g.drawString("<  " + q.getSelectedOption() + "  >", xpos + 100, ypos + yshift);
			yshift += 20;
		}
	}

}
