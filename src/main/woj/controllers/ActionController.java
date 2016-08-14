package main.woj.controllers;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.woj.gameplay.Board;
import main.woj.gameplay.Category;
import main.woj.gameplay.Game;
import main.woj.gameplay.Question;
import main.woj.gameplay.Wheel.StaticCategory;
import main.woj.ui.GameFrame;
import main.woj.ui.QuestionDialog;

public class ActionController implements Observer {
	private Game gameModel;
	private GameFrame gameFrame;
	public ActionController(String questionSet){
		gameModel = new Game(questionSet);
		gameFrame = new GameFrame(this);
		gameModel.addObserver(this);
		gameFrame.updateScore();
		gameFrame.updateActionIndicator();
	}

	@Override
	public void update(Observable o, Object arg) {
		//Called after each turn
		if (gameModel.getLastTurn() != null){
			gameFrame.updateBoard();
			gameFrame.updateScore();
			gameFrame.updateActionIndicator();
		}
	}
	
	public Game getGame() {
		return gameModel;
	}
	
	public Board getBoard() {
		return gameModel.getBoard();
	}
		
	public void startNewGame(){
		gameModel.play();
		gameFrame.showWheel();
	}
	
	public void initNewTurn(){
		
	}
	
	public void handleSpin(StaticCategory spinResult, int spinCount){
		gameFrame.updateSpinCount(spinCount);
		delegateSpinResultAction(spinResult);
		promptSpin();
	}
	
	private void delegateSpinResultAction(StaticCategory spinResult){
		switch(spinResult){
			case CATEGORY_1:
			case CATEGORY_2:
			case CATEGORY_3:
			case CATEGORY_4:
			case CATEGORY_5:
			case CATEGORY_6:
				JOptionPane.showMessageDialog(gameFrame, "Answer a Question from the " + gameModel.getCategoryKey(spinResult) + " Category!");
				//System.out.println("Answering a question...");
				promptNextQuestion(spinResult);
				break;
			case LOSE_TURN:
				JOptionPane.showMessageDialog(gameFrame, "Lost your turn!");
				//System.out.println("Lost your turn!");
				handleLoseTurn(spinResult);
				break;
			case FREE_TURN:
				JOptionPane.showMessageDialog(gameFrame, "Free turn!");
				//System.out.println("Free turn!");
				handleFreeTurn();
				break;
			case BANKRUPT:
				JOptionPane.showMessageDialog(gameFrame, "Bankrupt!");
				//System.out.println("Bankrupt!");
				handleBankrupt();
				break;
			case OPPONENT_CHOICE:
				JOptionPane.showMessageDialog(gameFrame, "Opponent's Choice!");
				handlePlayerChoice();
				break;
			case PLAYER_CHOICE:
				JOptionPane.showMessageDialog(gameFrame, "Player Choice!");
				//System.out.println("Player Choice!");
				handlePlayerChoice();
				break;
			case SPIN_AGAIN:
				JOptionPane.showMessageDialog(gameFrame, "Spin Again!");
				//System.out.println("Spin again!");
				handleSpinAgain();
				break;
		}
	}

	private void handleBankrupt() {
		gameModel.handleBankrupt();
		gameModel.takeTurn();
	}

	public void promptSpin(){
		gameFrame.showWheel();
	}
	
	public void promptNextQuestion(StaticCategory spinResult){
		gameFrame.showBoard();
		Question question = gameModel.getNextQuestion(spinResult);
		String categoryKey = gameModel.getCategoryKey(spinResult);
		gameModel.answerQuestion(categoryKey, question, QuestionDialog.showQuestion(gameFrame, question));
		gameModel.takeTurn();
	}

	public void promptNextQuestion(Category category){
		gameFrame.showBoard();
		Question question = category.nextQuestion();
		gameModel.answerQuestion(category.title(), question, QuestionDialog.showQuestion(gameFrame, question));
	}
	
	public void handlePlayerChoice() {
		Category category = promptForCategorySelection();
		promptNextQuestion(category);
		gameModel.takeTurn();
	}
	
	public void handleSpinAgain() {
		//TODO - prompt user
	}

	public void handleLoseTurn(StaticCategory spinResult) {
		//Spin again if player has extra turns
		gameModel.handleLoseTurn();
		if(gameModel.getCurrentPlayer().getExtraTurnsCounter() > 0){
			gameModel.getCurrentPlayer().decrementExtraTurnsCounter();
		}else{
			gameModel.takeTurn();
		}
	}
	
	public void handleFreeTurn() {
		gameModel.handleFreeTurn();
	}

	
	private Category promptForCategorySelection(){
		JPanel panel = new JPanel(); 
		//This for loop is to set the number of button selections
		int numCategory = 0; 
		for (Category category : gameModel.getCategories())
		{
			if (category.questionCollection.hasNext())
			{
				numCategory++;
			}
		}
		String [] titles = new String [numCategory]; 
		//This for loop is to label the button selections
		int countTitles = 0; 
		for (Category category : gameModel.getCategories()){
			if (category.questionCollection.hasNext())
			{
				titles[countTitles] = category.title(); 
				System.out.println(titles[countTitles]);
				countTitles++;
			}
		}
		if (titles[0].equals(null))
		{
			titles[0] = "Round Over";
		}
		
		JLabel label = new JLabel("Select one Category below: ");
		panel.add(label);
		int selectedOption = JOptionPane.showOptionDialog(null, panel, "Choose a category:", 
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, titles , null);
		
		if (selectedOption < titles.length)
		    if (gameModel.getBoard().hasCategory(titles[selectedOption]))
		    {
		    	return gameModel.getBoard().getCategory(titles[selectedOption]);
		    }
		    else if (titles[selectedOption] == "Round Over")
		    {
		    	JOptionPane.showMessageDialog(null, "This round is over.");
		    	return null;
		    }
		    else
		    {
		    	return promptForCategorySelection();
		    }
		else
		{
			return promptForCategorySelection();
		}
		
//		System.out.println("Choose a category:");
//		for (Category category : gameModel.getCategories()){
//			System.out.println(category.title());
//		}
//		System.out.print("Category: ");
//		String categoryTitle = scanner.next();
//
//		if( gameModel.getBoard().hasCategory(categoryTitle) ){
//			return gameModel.getBoard().getCategory(categoryTitle);
//		}else{
//			//recursively call self until user inputs expected value
//			return promptForCategorySelection();
//		}
	}
}
