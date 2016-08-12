package main.woj.gameplay;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import com.esotericsoftware.yamlbeans.YamlException;

import main.woj.gameplay.Wheel.StaticCategory;
import main.woj.utils.QuestionDeserializer;

public class Game extends Observable{
	private final int ROUND_ONE_MULTIPLER = 200;
	private final int ROUND_TWO_MULTIPLER = 400;
	private final int TURNS_PER_ROUND = 50;
	private ArrayList<Player> players;
	private Board board;
	private Wheel wheel;
	private ArrayList<Category> categories;
	private int turns;
	private Scanner scanner;
	private Stack<Turn> turnCache;
	
	public Game(String questionSet){
		initGame(questionSet);
		scanner = new Scanner(System.in);
	}
	
	public void play(){
		System.out.println("Starting game...");
		//Reset values if necessary
	}
	
	public Board getBoard(){
		return board;
	}
	
	private Boolean roundOneCompleted(){
		return turns >= TURNS_PER_ROUND;	
	}
	
	public void takeTurn(){
		printStatus();
		//handleSpinResult(spinResult);
		turns++;
		this.setChanged();
		notifyObservers();
	}
	
	private void printStatus(){
		System.out.println("\nTurn: " + turns + " - " + getCurrentPlayer().getName() + " ($"+ getCurrentPlayer().getGameScore() + ")" + " just finished spinning the wheel...");
	}
	
	private int getMultiplier(){
		return roundOneCompleted() ? ROUND_TWO_MULTIPLER : ROUND_ONE_MULTIPLER;
	}
	
	private Boolean askQuestion(Question question){
		System.out.println(question.ask());
		System.out.print("Answer: ");
		String answer = scanner.next();
		return question.verifyAnswer(answer.trim()); 
	}
	
	private void updatePlayerScore(String categoryKey, Question question,Boolean isCorrect){
		int questionValue = question.value(getMultiplier());
		int pointsEarned = isCorrect ? questionValue : (-1*questionValue);
		getCurrentPlayer().updatePlayerScore(pointsEarned);
		
		addTurnToCache(new Turn(categoryKey, question, isCorrect));

		System.out.println("Your answer is " + (isCorrect ? "correct" : "incorrect") +". Updating value by $" + pointsEarned);
		System.out.println("Current earnings: $" + getCurrentPlayer().getGameScore());
	}
	
	private void addTurnToCache(Turn turn) {
		turnCache.push(turn);
	}
	
	public Turn getLastTurn(){
		if (turnCache.empty()){
			//prevents it from breaking. 
			return null;
		}
		else{
			return turnCache.peek();
		}
	}

	private void handleSpinResult(StaticCategory spinResult){
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

	public Question getNextQuestion(StaticCategory spinResult){
		Category category = categories.get(spinResult.ordinal());
		return category.nextQuestion();
	}
	
	public String getCategoryKey(StaticCategory spinResult){
		return categories.get(spinResult.ordinal()).title();
	}
	
	public void answerQuestion(String categoryKey, Question question, String response){
		Boolean correct = question.verifyAnswer(response);
		updatePlayerScore(categoryKey, question, correct);
	}
	
	public void handleQuestionSpin(StaticCategory spinResult){
		Category category = categories.get(spinResult.ordinal());
		promptNextQuestion(category);
	}

	private void promptNextQuestion(Category category) {
		Question question = category.nextQuestion();
		Boolean result = askQuestion(question);
		updatePlayerScore(category.getTitle(), question, result);
	}
	
	public void handleSpinAgain() {
		//handleSpinResult(wheel.spin());//spin again
	}

	public void handleOpponentChoice() {
		Category category = promptForCategorySelection();
		promptNextQuestion(category);	
	}

	public void handlePlayerChoice() {
		Category category = promptForCategorySelection();
		promptNextQuestion(category); 
	}

	private Category promptForCategorySelection(){
		//This is not used. The one being used is in action controller.
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
	
	public void handleBankrupt() {
		getCurrentPlayer().makePlayerBankrupt();
	}

	public void handleLoseTurn() {
		//Spin again if player has extra turns
		if(getCurrentPlayer().getExtraTurnsCounter() > 0){
			getCurrentPlayer().decrementExtraTurnsCounter();
			//handleSpinResult(wheel.spin());//spin again
		}
	}
	
	public void handleFreeTurn() {
		getCurrentPlayer().incrementExtraTurnsCounter();
		//handleSpinResult(wheel.spin());//spin again
	}

	public Player getPlayer1Information()
	{
		return players.get(0);
	}
	
	public Player getPlayer2Information()
	{
		return players.get(1);
	}
	
	public Player getCurrentPlayer(){
		return players.get(turns % players.size());
	}
	
	private void initGame(String questionSet){
		turnCache = new Stack();
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
	
	public class Turn{
		
		private Boolean result;
		private Question question;
		private String categoryKey;

		public Turn(String categoryKey, Question question, Boolean result){
			this.categoryKey = categoryKey;
			this.question = question;
			this.result = result;
		}
		
		public Category getCategory(){
			return board.getCategory(categoryKey);
		}
		
		public Question getQuestion(){
			return question;
		}
		
		public Boolean getResult(){
			return result;
		}
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}
}
