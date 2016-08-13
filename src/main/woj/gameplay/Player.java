package main.woj.gameplay;
public class Player 
{
	//Class Data Members
	private String name;
	private String lastName;
	private int gameScore;
	private int extraTurnsCounter;
	private int correctQuestionsCounter;
	private int missedQuestionsCounter;
	
	//Class Constructors
	public Player(String firstName)
	{
		this.name = firstName;
		this.gameScore = 0;
		this.extraTurnsCounter = 0;
		this.correctQuestionsCounter = 0;
		this.missedQuestionsCounter = 0;
	}
	
	public Player(String firstName, String lastName, int gameScore, 
		int extraTurnsCounter, int correctQuestionsCounter, int missedQuestionCounter)
	{
		this.name = firstName;
		this.gameScore = gameScore;
		this.extraTurnsCounter = extraTurnsCounter;
		this.missedQuestionsCounter = missedQuestionsCounter;
	}
	
	//Getters and Setters
	public String getName(){return this.name;}
	public int getGameScore(){return this.gameScore;}
	public int getExtraTurnsCounter(){return this.extraTurnsCounter;}
	public int getCorrectQuestionsCounter(){return this.correctQuestionsCounter;}
	public int getMissedQuestionsCounter(){return this.missedQuestionsCounter;}
	
	public void setName(String firstName){this.name = firstName;}
	public void setGameScore(int gameScore){this.gameScore = gameScore;}
	public void setExtraTurnsCounter(int extraTurnsCounter){this.extraTurnsCounter = extraTurnsCounter;}
	public void setCorrectQuestionsCounter(int correctQuestionsCounter){this.correctQuestionsCounter = correctQuestionsCounter;}
	public void setMissedQuestionsCounter(int missedQuestionsCounter){this.missedQuestionsCounter = missedQuestionsCounter;}
	
	
	public boolean makePlayerBankrupt(){this.gameScore = 0;return true;}
	
	public void incrementExtraTurnsCounter(){this.extraTurnsCounter++;}
	public void decrementExtraTurnsCounter(){this.extraTurnsCounter--;}
	public void updatePlayerScore(int pointsEarned){this.gameScore += pointsEarned;}
	public void incrementCorrectQuestionsCounter(){this.correctQuestionsCounter += 1;}
	public void incrementMissedQuestionsCounter(){this.missedQuestionsCounter += 1;}
}