package core;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private GraphicsDevice graphicsDevice;
	private InputManager inputManager;

	public GameFrame(GameWindowAdapter windowAdapter) {
		super();
		this.setIgnoreRepaint(true);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setFocusTraversalKeysEnabled(false);
		this.graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		// graphicsDevice.setFullScreenWindow(this);
		this.setSize(1300, 700);
		this.setLocation(10, 50);
		this.setVisible(true);

		// DisplayMode displayMode = new DisplayMode(1366, 768, 32,
		// DisplayMode.REFRESH_RATE_UNKNOWN);
		// graphicsDevice.setDisplayMode(displayMode);

		Settings.GAME_FRAME_WIDTH = (int) getWidth();
		Settings.GAME_FRAME_HEIGHT = (int) getHeight();
		addWindowListener(windowAdapter);
	}

	public void setInputManager(InputManager im) {
		this.inputManager = im;
		addKeyListener(new KL());
		addMouseMotionListener(new MML());
		addMouseListener(new ML());
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