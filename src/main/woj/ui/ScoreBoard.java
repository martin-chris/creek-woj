package main.woj.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
//import java.awt.GridLayout;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.woj.gameplay.Game;
import main.woj.gameplay.Player;

public class ScoreBoard extends JPanel{
	private JLabel playerOneDisplay;
	private JLabel playerTwoDisplay;
	
	public ScoreBoard(){
		setup();
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new GridLayout(2,1));
		playerOneDisplay = new JLabel();
		playerTwoDisplay = new JLabel();
		playerOneDisplay.setForeground(Color.RED);
		playerTwoDisplay.setForeground(Color.RED);
		add(playerOneDisplay);
		add(playerTwoDisplay);
	}
	
	private void setup() {
		this.setLayout(new GridLayout(1,1));
	}

	public String scoreDisplay(Player player){
		return "Name: " + player.getName() + " " + " Score: $" + player.getGameScore()
		+ " " + " Tokens: " + player.getExtraTurnsCounter();
	}
	
	public void updateScore(Player player1, Player player2) {
		playerOneDisplay.setText(scoreDisplay(player1));
		playerTwoDisplay.setText(scoreDisplay(player2));
		this.validate();
		this.repaint();
	}
}


