package core;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static String title = "stratstuff v0.1";
	private InputManager inputManager;

	public GameFrame(InputManager inputHandler, GameWindowAdapter windowAdapter) {
		super(title);
		System.out.println("test");
		this.inputManager = inputHandler;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) screen.getWidth(), (int) screen.getHeight() - 40);
		Settings.GAME_FRAME_WIDTH = (int) screen.getWidth();
		Settings.GAME_FRAME_HEIGHT = (int) screen.getHeight() - 40;
		setLocation(Settings.GAME_FRAME_XPOS, Settings.GAME_FRAME_YPOS);
		addWindowListener(windowAdapter);
	}
}