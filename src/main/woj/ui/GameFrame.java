package main.woj.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame{
	private LandingPage landingPage;

	public GameFrame(){
		super("Wheel of Jeopardy");
		initComponents();
		setup();
		this.repaint();
	}

	private void initComponents() {
		add(new LandingPage());
	}
	
	private void setup(){
		this.setSize(800,600);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
