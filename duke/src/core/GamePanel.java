package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private Image bufImage;
	private Graphics bufG;

	private InputManager inputManager;
	private ViewManager viewManager;

	public GamePanel(InputManager inputManager, ViewManager viewMgr) {
		this.inputManager = inputManager;
		this.viewManager = viewMgr;
		setFocusTraversalKeysEnabled(false);
		addKeyListener(new KL());
		addMouseMotionListener(new MML());
		addMouseListener(new ML());
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		viewManager.draw(g2);
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

	private class ML implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			inputManager.mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			inputManager.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			inputManager.mouseReleased(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	private class MML implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			inputManager.mouseDragged(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			inputManager.mouseMoved(e);
		}

	}

}
