package main.woj.controllers;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
	private Scanner scanner;
	public ActionController(String questionSet){
		gameModel = new Game(questionSet);
		gameFrame = new GameFrame(this);
		gameModel.addObserver(this);
		scanner = new Scanner(System.in);
	}

	@Override
	public void update(Observable o, Object arg) {
		//Called after each turn
		if (gameModel.getLastTurn() != null){
			gameFrame.updateBoard(gameModel.getLastTurn());
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
	
	public void startNewTurn(StaticCategory spinResult){
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
				System.out.println("Answering a question...");
				promptNextQuestion(spinResult);
				break;
			case LOSE_TURN:
				System.out.println("Lost your turn!");
				handleLoseTurn(spinResult);
				break;
			case FREE_TURN:
				System.out.println("Free turn!");
				handleFreeTurn();
				break;
			case BANKRUPT:
				System.out.println("Bankrupt!");
				handleBankrupt();
				break;
			case OPPONENT_CHOICE:
			case PLAYER_CHOICE:
				System.out.println("Player Choice!");
				handlePlayerChoice();
				break;
			case SPIN_AGAIN:
				System.out.println("Spin again!");
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
		System.out.println("Choose a category:");
		for (Category category : gameModel.getCategories()){
			System.out.println(category.title());
		}
		System.out.print("Category: ");
		String categoryTitle = scanner.next();

		if( gameModel.getBoard().hasCategory(categoryTitle) ){
			return gameModel.getBoard().getCategory(categoryTitle);
		}else{
			//recursively call self until user inputs expected value
			return promptForCategorySelection();
		}
	}
}