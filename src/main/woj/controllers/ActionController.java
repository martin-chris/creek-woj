package main.woj.controllers;

import java.util.Observable;
import java.util.Observer;

import main.woj.gameplay.Board;
import main.woj.gameplay.Game;
import main.woj.gameplay.Wheel.StaticCategory;
import main.woj.ui.GameFrame;

public class ActionController implements Observer {
	private Game gameModel;
	private GameFrame gameFrame;
	public ActionController(String questionSet){
		gameModel = new Game(questionSet);
		gameFrame = new GameFrame(this);
		gameModel.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		//Called after each turn
		gameFrame.updateBoard(gameModel.getLastTurn());
	}
	
	public Game getGame() {
		return gameModel;
	}
	
	public Board getBoard() {
		return gameModel.getBoard();
	}
		
	public void startNewGame(){
		gameModel.play();
	}
	
	public void startNewTurn(StaticCategory spinResult){
		gameModel.takeTurn(spinResult);
	}
}
