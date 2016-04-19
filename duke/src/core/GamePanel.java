package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private Image bufImage;
	private Graphics bufG;

	private InputManager inputManager;
	private ViewManager viewManager;

	public GamePanel(InputManager inputManager, ViewManager viewMgr) {
		this.inputManager = inputManager;
		this.viewManager = viewMgr;
		addKeyListener(new KL());
	}

	@Override
	public void paint(Graphics g) {
		viewManager.draw((Graphics2D) g);
	}

	@Override
	public void update(Graphics g) {
		int w = this.getSize().width;
		int h = this.getSize().height;

		if (bufImage == null) {
			bufImage = this.createImage(w, h);
			bufG = bufImage.getGraphics();
		}

		bufG.setColor(this.getBackground());
		bufG.fillRect(0, 0, w, h);

		bufG.setColor(this.getForeground());

		paint(bufG);

		g.drawImage(bufImage, 0, 0, this);
	}

	private class KL implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			inputManager.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			inputManager.keyReleased(e);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}

}
