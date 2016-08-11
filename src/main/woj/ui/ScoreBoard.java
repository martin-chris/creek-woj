package main.woj.ui;

//import java.awt.GridLayout;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.woj.gameplay.Game;

public class ScoreBoard extends JPanel{
	// private JLabel label;
	// public ScoreBoard(){
	// 	setup();
	// 	initComponents();
	// }
	
	// private void initComponents(){
	// 	label = new JLabel("Scoreboard");
	// 	add(label);
	// }
	
	// private void setup() {
	// 	this.setSize(this.getMaximumSize());
	// 	this.setLayout(new GridLayout(1,1));
	// }
	
	private static String player1Name; 
	private static String player2Name; 
	private static int player1Score;
	private static int player2Score; 
	private static String currentWinner; 
	private static int currentWinnerScore; 
	
	public static void showScoreBoard(Game gameModel) 
	{
		player1Name = gameModel.getPlayer1Information().getName(); 
		player2Name = gameModel.getPlayer2Information().getName();
		player1Score = gameModel.getPlayer1Information().getGameScore(); 
		player2Score = gameModel.getPlayer2Information().getGameScore(); 
		currentWinner = ""; 
		currentWinnerScore = 0; 
		
		if (player1Score > player2Score)
		{
			currentWinner = player1Name; 
			currentWinnerScore = player1Score; 
		}
		else if (player1Score < player2Score)
		{
			currentWinner = player2Name; 
			currentWinnerScore = player2Score; 
		}
		else
		{
			currentWinner = "Tie"; 
			currentWinnerScore = player1Score; 
		}
		
		
		String mainMessage = "Player 1 Information" + "\n" + "Name: " + player1Name + "\n" + "Score: " + Integer.toString(player1Score)
			+ "\n" + "\n" + "Player 2 Information" + "\n" + "Name: " + player2Name + "\n" + "Score: " + Integer.toString(player2Score)
			+ "\n" + "\n" + "Current Winner Information" + "\n" + "Name: " + currentWinner + "\n" + "Score: " + Integer.toString(currentWinnerScore)
			; 
		
		JOptionPane.showMessageDialog(null, mainMessage,"Score Board", 1);	
	}
}


