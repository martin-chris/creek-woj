package main.woj.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LandingPage extends JPanel {
	private AboutPage aboutPage;
	private GameplayPage gamePage;
	private JButton aboutPageButton;
	private JButton gamePageButton;
	private GameFrame gameFrame;

	public LandingPage(GameFrame gameFrame){
		this.gameFrame = gameFrame;
		this.setBackground(Color.green);
		setup();
		initComponents();
		initListeners();
	}
	
	private void setup() {
		setSize(this.getMaximumSize());
	}

	private void initListeners() {
        //Process the Apply gaps button press
		aboutPageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				AboutPage.showAbout();
			}
        });
		
		gamePageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				transitionToGame();
			}
        });
	}

	private void transitionToGame(){
		gameFrame.initNewGame();
	}

	private void initComponents() {
		add(Box.createVerticalStrut(800));
		
		JPanel buttonPanel = new JPanel();
		aboutPageButton = new JButton("About");
		gamePageButton = new JButton("New Game");
		buttonPanel.add(aboutPageButton);
		buttonPanel.add(gamePageButton);

		add(buttonPanel);
		this.repaint();
	}
}
