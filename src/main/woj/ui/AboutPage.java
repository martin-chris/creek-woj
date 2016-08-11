package main.woj.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.woj.gameplay.Question;

public class AboutPage 
{
	public static void showAbout()
	{
		
		String header1= "Introduction"; 
		String header2 = "Game Rules";
		String header3 = "How to Play"; 
		String header4 = "About Us"; 
		String header5 = "Terms of Use";
	
		String introduction= "Welcome to the Wheel of Jeopardy! This educational game" + "\n"
				+ "combines the Wheel of Fortune and the Jeopardy game into one. In the" + "\n"
				+ "original Wheel of Fortune game, players would spin the wheel for a" + "\n"
				+ "value and select a letter to solve a word puzzle. In the original Jeopardy" + "\n"
				+ "game, players would advance to three rounds with each rounds with questions" + "\n"
				+ "worth more money and compete in six different categories by supplying the " + "\n"
				+ "questions to the answers. Therefore, in the Wheel of Jeopardy, players will" + "\n"
				+ "spin the wheel to get the category, question, and question point value. " + "\n"
				+ "If answered correctly, players will gain points. The one with the highest" + "\n"
				+ "points wins!"
				; 
		String gameRules = "The players take turns spinning the wheel and answering questions." + "\n"
				+ "When it is your turn, you will spin the wheel, and do whatever" + "\n"
				+ " it says on the sector of the wheel. There are 12 sectors: Lose Turn" + "\n"
				+ "(Player misses a turn), Free Turn (Player gets a token to redeem a " + "\n"
				+ "turn if question is answer incorrectly or player spins 'lose turn')," + "\n"
				+ "Bankrupt (Player loses all points), Spin again (Player gets to spin again)" + "\n"
				+ "one sector for each question type (wheel selects 1 out of 6 category created" + "\n"
				+ "by the imported question set), Player's Choice (player selects own category)," + "\n"
				+ "Opponent's Choice (opponent selects category)." + "\n" + "\n"
				+ "For the last three types of sectors (Wheel Selection, Player Selection, or " + "\n"
				+ "Opponent Selection), if player answers correctly, points added. If answers " + "\n"
				+ "incorrectly, points subtracted.  "
				; 
		String howToPlay = "There are two rounds in the game, whereby the second round doubles all" + "\n"
				+ "question point values. The player with the largest total score from both rounds" + "\n"
				+ "WINS the game. In each round, there are a total number of 50 spins." + "\n"
				+ "As the player, you will click the 'Spin' button to spin the wheel. Once you get" + "\n"
				+ "a question, you answer it within the time alloted. Then, the next player gets" + "\n"
				+ "their turn. This continues until the round is over."
				;
		String aboutUs = "This game was created by The Creek Entertainment as an educational software."; 
		String termsOfUse= "Disclaimer: If you suffer from photosensitive epilepsy, which" + "\n"
				+ "is epilepsy induced by lights or animations, we suggest you refrain" + "\n"
				+ "from playing this game. " + "\n" + "\n"
				+ "This product is released with the BSD 3-clause license." + "\n" + "\n"
				+ "This product complies with FERPA, IDEA, Section 504 of the Rehabilitation" + "\n"
				+ "Act of 1973, and ADA."
				;
		
		
		String mainMessage = header1 + "\n" +  introduction + "\n" + "\n" 
				+header2  + "\n" +  gameRules + "\n" + "\n" +header3 
				+ "\n" +  howToPlay + "\n" + "\n" + header4 + "\n" 
				+  aboutUs + "\n" + "\n" + header5  + "\n" +  termsOfUse; 
		JTextArea textArea = new JTextArea(mainMessage); 
		JScrollPane scrollPane = new JScrollPane(textArea); 
		scrollPane.setPreferredSize(new Dimension(500,500));
		JOptionPane.showMessageDialog(null, scrollPane,"About", 1);
	}
}
