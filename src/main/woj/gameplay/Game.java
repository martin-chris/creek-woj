package main.woj.gameplay;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.esotericsoftware.yamlbeans.YamlException;

import main.woj.gameplay.Wheel.StaticCategory;
import main.woj.utils.QuestionDeserializer;

public class Game {
	private final int ROUND_ONE_MULTIPLER = 200;
	private final int ROUND_TWO_MULTIPLER = 400;
	private final int TURNS_PER_ROUND = 50;
	private ArrayList<Player> players;
	private Board board;
	private Wheel wheel;
	private ArrayList<Category> categories;
	private int turns;
	private Scanner scanner;
	
	public Game(String questionSet){
		initGame(questionSet);
		scanner = new Scanner(System.in);
	}
	
	public void play(){
		System.out.println("Starting game...");

		while(!roundOneCompleted()){
			takeTurn();
		}
	}
	
	private Boolean roundOneCompleted(){
		return turns >= TURNS_PER_ROUND;	
	}
	
	public void takeTurn(){
		printStatus();
		handleSpinResult(wheel.spin());
		turns++;
	}
	
	public void printStatus(){
		try {
			Thread.sleep(1000);
			System.out.println("\nTurn: " + turns + " - " + getCurrentPlayer().getFirstName() + " ($"+ getCurrentPlayer().getGameScore() + ")" + " is spinning the wheel...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int getMultiplier(){
		return roundOneCompleted() ? ROUND_TWO_MULTIPLER : ROUND_ONE_MULTIPLER;
	}
	
	public Boolean askQuestion(Question question){
		System.out.println(question.ask());
		System.out.print("Answer: ");
		String answer = scanner.next();
		return question.verifyAnswer(answer.trim()); 
	}
	
	public void updatePlayerScore(Question question,Boolean isCorrect){
		int questionValue = question.value(getMultiplier());
		int pointsEarned = isCorrect ? questionValue : (-1*questionValue);
		getCurrentPlayer().updatePlayerScore(pointsEarned);

		System.out.println("Your answer is " + (isCorrect ? "correct" : "incorrect") +". Updating value by $" + pointsEarned);
		System.out.println("Current earnings: $" + getCurrentPlayer().getGameScore());
	}
	
	public void handleSpinResult(StaticCategory spinResult){
		switch(spinResult){
			case CATEGORY_1:
			case CATEGORY_2:
			case CATEGORY_3:
			case CATEGORY_4:
			case CATEGORY_5:
			case CATEGORY_6:
				handleQuestionSpin(spinResult);
				break;
			case LOSE_TURN:
				System.out.println("Lose Turn - " + getCurrentPlayer().getExtraTurnsCounter() + " extra turns remaining");
				handleLoseTurn();
				break;
			case FREE_TURN:
				System.out.println("Free turn - " + getCurrentPlayer().getExtraTurnsCounter()+1 + " extra turns available");
				handleFreeTurn();
				break;
			case BANKRUPT:
				System.out.println("Bankrupt");
				handleBankrupt();
				break;
			case PLAYER_CHOICE:
				System.out.println("Player choice");
				handlePlayerChoice();
				break;
			case OPPONENT_CHOICE:
				System.out.println("Opponent choice");
				handleOpponentChoice();
				break;
			case SPIN_AGAIN:
				System.out.println("Spin again");
				handleSpinAgain();
				break;
		}
	}

	public void handleQuestionSpin(StaticCategory spinResult){
		Category category = categories.get(spinResult.ordinal());
		promptNextQuestion(category);
	}

	private void promptNextQuestion(Category category) {
		Question question = category.nextQuestion();
		Boolean result = askQuestion(question);
		updatePlayerScore(question, result);
	}
	
	private void handleSpinAgain() {
		getCurrentPlayer().incrementExtraTurnsCounter();
		handleSpinResult(wheel.spin());//spin again
	}

	private void handleOpponentChoice() {
		Category category = promptForCategorySelection();
		promptNextQuestion(category);	
	}

	private void handlePlayerChoice() {
		Category category = promptForCategorySelection();
		promptNextQuestion(category); 
	}

	private Category promptForCategorySelection(){
		System.out.println("Choose a category:");
		for (Category category : categories){
			System.out.println(category.title());
		}
		System.out.print("Category: ");
		String categoryTitle = scanner.next();

		if( board.hasCategory(categoryTitle) ){
			return board.getCategory(categoryTitle);
		}else{
			//recursively call self until user inputs expected value
			return promptForCategorySelection();
		}
	}
	
	private void handleBankrupt() {
		getCurrentPlayer().makePlayerBankrupt();
	}

	private void handleLoseTurn() {
		//Spin again if player has extra turns
		if(getCurrentPlayer().getExtraTurnsCounter() > 0){
			getCurrentPlayer().decrementExtraTurnsCounter();
			handleSpinResult(wheel.spin());//spin again
		}
	}
	
	private void handleFreeTurn() {
		getCurrentPlayer().incrementExtraTurnsCounter();
		handleSpinResult(wheel.spin());//spin again
	}

	private Player getCurrentPlayer(){
		return players.get(turns % players.size());
	}
	
	private void initGame(String questionSet){
		turns = 0;
		players = new ArrayList<Player>();
		players.add(new Player("Player 1"));
		players.add(new Player("Player 2"));
		try {
			categories = QuestionDeserializer.importQuestionSet(questionSet);
			wheel = new Wheel();
			board = new Board(categories);
		} catch (YamlException | FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
