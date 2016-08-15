package main.woj.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import main.woj.gameplay.Board;
import main.woj.gameplay.Category;
import main.woj.gameplay.Game;
import main.woj.gameplay.Player;
import main.woj.gameplay.Question;
import main.woj.gameplay.Wheel.StaticCategory;
import main.woj.ui.GameFrame;
import main.woj.ui.QuestionDialog;

public class ActionController implements Observer {
	public Game gameModel;
	private GameFrame gameFrame;
	private Timer animationTimer;
	private ActionListener animationListener;
	private Timer showBoardUpdateTimer;
	private ActionListener updateBoardListener;
	private Boolean roundOneCompleteFlag = false;
	private String questionSet1;
	private String questionSet2;
	public ActionController(String questionSet1, String questionSet2){
		this.questionSet1 = questionSet1;
		this.questionSet2 = questionSet2;
		initNewGame();
	}

	public void initNewGame(){
		gameModel = new Game(questionSet1, questionSet2);
		gameFrame = new GameFrame(this);
		gameModel.addObserver(this);
		gameFrame.updateScore();
		gameFrame.updateActionIndicator();		
	}
	
	public void initPlayerNames(){
		JOptionPane.showMessageDialog(null, "<html>Welcome to the Wheel of Jeopardy. "
				+ "<br>Before the game begins, please enter the player's names. </html>");
		String player1Name, player2Name; 

		String displayPlayer1NameRequest = "Enter Player 1's Name: "; 
		String displayPlayer2NameRequest = "Enter Player 2's Name: ";
		
		player1Name = promptForPlayerName(displayPlayer1NameRequest);
		player2Name = promptForPlayerName(displayPlayer2NameRequest);
		gameModel.getPlayer1Information().setName(player1Name);
		gameModel.getPlayer2Information().setName(player2Name);
	}
	
	private String promptForPlayerName(String requestPrompt)
	{
		String[] buttons = {"Ok"};
		JPanel panel = new JPanel();
		String playerName = "";
		JLabel label = new JLabel(requestPrompt); 
		JTextField text = new JTextField(10); 
		panel.setLayout(new BoxLayout (panel, BoxLayout.Y_AXIS));
		panel.add(label);
		panel.add(text);
		
		int selectedOption = JOptionPane.showOptionDialog(null, panel, requestPrompt, 
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons , buttons[0]);

		if(selectedOption == 0)
		{
		    String userInput = text.getText();
		    if (userInput.isEmpty() || userInput == null || userInput.trim().length() == 0 )
		    {
		    	return promptForPlayerName(requestPrompt);
		    }
		    else
		    {
		    	playerName = userInput;
		    	return playerName; 
		    }
		} 
		else
		{
			return promptForPlayerName(requestPrompt);
		}	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//Called after each turn
		updateBottomPanel();
		if (gameModel.getLastTurn() != null){
			gameFrame.updateBoard();
		}
		checkRoundStatus();
		promptSpin();
	}
	
	private void announceGameOver(){
		Player winner = gameModel.getCurrentWinner();
		if(winner == null){
			JOptionPane.showMessageDialog(gameFrame, "Game Over!\nThe game is a tie!");
		}else{
			JOptionPane.showMessageDialog(gameFrame, "Game Over!\nThe winner is: "+ winner.getName());
		}
		initNewGame();
	}
	
	private void checkRoundStatus(){
		if(gameModel.roundOneCompleted() && !roundOneCompleteFlag){
			roundOneCompleteFlag = true;
			initRoundTwo();
		}else if(gameModel.roundTwoCompleted()){
			this.announceGameOver();
		}
	}
	
	private void initRoundTwo() {
		showRoundOvereNotice();
		gameModel.advanceToRoundTwo();
		gameFrame.initRoundTwo();
	}

	private void showRoundOvereNotice() {
		JOptionPane.showMessageDialog(gameFrame, "Round One Complete. Beginning Round Two.");
	}

	private void updateBottomPanel(){
		gameFrame.updateScore();
		gameFrame.updateActionIndicator();
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
		
		//Round check is handled elsewhere for questions
		if(!isCategorySpin(spinResult)){
			checkRoundStatus();
		}
	}
	
	private boolean isCategorySpin(StaticCategory spinResult){
		switch(spinResult){
			case CATEGORY_1:
			case CATEGORY_2:
			case CATEGORY_3:
			case CATEGORY_4:
			case CATEGORY_5:
			case CATEGORY_6:
				return true;
			default:
				return false;
		}
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
		updateBottomPanel();
	}

	private void handleBankrupt() {
		gameModel.handleBankrupt();
		gameModel.takeTurn();
	}

	public void promptSpin(){
		setupUpdateBoardAnimation();
		showBoardUpdateTimer.start();//start the task
	}
	
	private void setupUpdateBoardAnimation(){
        updateBoardListener = new ActionListener() {
            public void actionPerformed(ActionEvent evt){
            	gameFrame.showWheel();
            }
        };
		showBoardUpdateTimer = new Timer(500,updateBoardListener);//create the timer which calls the actionperformed method for every 1000 millisecond(1 second=1000 millisecond)
		showBoardUpdateTimer.setRepeats(false);

	}
	
	private void setupAskQuesitonAnimation(final StaticCategory spinResult) {
        animationListener = new ActionListener() {
            public void actionPerformed(ActionEvent evt){
        		Question question = gameModel.getNextQuestion(spinResult);
        		String categoryKey = gameModel.getCategoryKey(spinResult);
        		gameModel.answerQuestion(categoryKey, question, QuestionDialog.showQuestion(gameFrame, question));

        		gameModel.takeTurn();
        		animationTimer.stop();
            }
        };
		animationTimer = new Timer(100,animationListener);//create the timer which calls the actionperformed method for every 1000 millisecond(1 second=1000 millisecond)
		animationTimer.setRepeats(false);
	}

	public void promptNextQuestion(final StaticCategory spinResult){
		if( gameModel.hasNextQuestion(spinResult)){
			gameFrame.showBoard();
			setupAskQuesitonAnimation(spinResult);
			animationTimer.start();//start the task
		} else {
			gameModel.hasNextQuestion(spinResult);
			JOptionPane.showMessageDialog(gameFrame, "No more questions for category. Spin again.");
		}
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
		//gameModel.handleLoseTurn();
		if(gameModel.getCurrentPlayer().getExtraTurnsCounter() > 0){
			int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to use a token?", "Redeem Token", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION )
			{
				gameModel.getCurrentPlayer().decrementExtraTurnsCounter();
			}
			else
			{
				gameModel.takeTurn();
			}
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
	}
}
