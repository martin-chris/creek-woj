package main.woj.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LandingPage extends JPanel {
	private AboutPage aboutPage;
	private GameplayPage gamePage;
	private JLabel applicationNameLabel;
	private JButton aboutPageButton;
	private JButton gamePageButton;
	private GameFrame gameFrame;
	private ImageIcon applicationMakerImage;

	public LandingPage(GameFrame gameFrame){
		this.gameFrame = gameFrame;
		this.setBackground(Color.blue);
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
//		add(Box.createVerticalStrut(800));
		
		JPanel buttonPanel = new JPanel();
		BufferedImage pic;
		
		//ImageIcon icon = new ImageIcon("/lib/team_image2.png");
		//JLabel imageLabel = new JLabel("",icon,JLabel.CENTER);
		
		JLabel imageLabel = new JLabel();

		
		try {
			pic = ImageIO.read(new File("lib/team_image2.png"));
			imageLabel.setIcon(new ImageIcon(pic));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
		
		
		this.applicationNameLabel = new JLabel("The Creek Entertainment");
		
		aboutPageButton = new JButton("About");
		gamePageButton = new JButton("New Game");
		
		add(imageLabel);

		buttonPanel.add(this.applicationNameLabel);
		buttonPanel.add(aboutPageButton);
		buttonPanel.add(gamePageButton);
		
		
		add(buttonPanel);
		this.repaint();
	}
}