package main.woj.gameplay;
class Player 
{
	//Class Data Members
	private String firstName;
	private String lastName;
	private int gameScore;
	private boolean playersTurnFlag;
	private boolean lostTurnFlag;
	private boolean bankruptFlag;
	private int extraTurnsCounter;
	private int correctQuestionsCounter;
	private int missedQuestionsCounter;
	
	//Class Constructors
	public Player(String firstName)
	{
		this.firstName = firstName;
		this.lastName = "";
		this.gameScore = 0;
		this.playersTurnFlag = false;
		this.lostTurnFlag = false;
		this.bankruptFlag = false;
		this.extraTurnsCounter = 0;
		this.correctQuestionsCounter = 0;
		this.missedQuestionsCounter = 0;
	}
	
	public Player(String firstName, String lastName, int gameScore, 
		boolean playersTurnFlag, boolean lostTurnFlag, boolean bankruptFlag,
		int extraTurnsCounter, int correctQuestionsCounter, int missedQuestionCounter)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.gameScore = gameScore;
		this.playersTurnFlag = playersTurnFlag;
		this.lostTurnFlag = lostTurnFlag;
		this.bankruptFlag = bankruptFlag;
		this.extraTurnsCounter = extraTurnsCounter;
		this.missedQuestionsCounter = missedQuestionsCounter;
	}
	
	//Getters and Setters
	public String getFirstName(){return this.firstName;}
	public String getLastName(){return this.lastName;}
	public int getGameScore(){return this.gameScore;}
	public boolean isPlayersTurn(){return this.playersTurnFlag;}
	public boolean playerLostTurn(){return this.lostTurnFlag;}
	public boolean isBankrupt(){return this.bankruptFlag;}
	public int getExtraTurnsCounter(){return this.extraTurnsCounter;}
	public int getCorrectQuestionsCounter(){return this.correctQuestionsCounter;}
	public int getMissedQuestionsCounter(){return this.missedQuestionsCounter;}
	
	public void setFirstName(String firstName){this.firstName = firstName;}
	public void setLastName(String lastName){this.lastName = lastName;}
	public void setGameScore(int gameScore){this.gameScore = gameScore;}
	public void setPlayersTurnFlag(boolean playersTurnFlag){this.playersTurnFlag = playersTurnFlag;}
	public void setLostTurnFlag(boolean lostTurnFlag){this.lostTurnFlag = lostTurnFlag;}
	public void setBankruptFlag(boolean bankruptFlag){this.bankruptFlag = bankruptFlag;}
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