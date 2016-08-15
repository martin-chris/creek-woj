package main.woj.ui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Game;
import main.woj.gameplay.Game.Turn;

public class GameFrame extends JFrame{
	private LandingPage landingPage;
	private GameplayPage gameplayPage;
	Game gameModel;
	private ActionController controller;
	private Turn lastTurn;

	public GameFrame(ActionController controller){
		super("Wheel of Jeopardy");
		this.controller = controller;
		this.gameModel = controller.getGame();
		setup();
		initComponents();
		this.validate();
	}

	private void initComponents() {
		this.setLayout(new GridLayout(0,1));
		landingPage = new LandingPage(this);
		gameplayPage = new GameplayPage(this, controller);
		add(landingPage);
	}
	
	private void setup(){
		this.setSize(800, 600);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void initNewGame() {
		controller.initPlayerNames();
		updateScore();
		updateActionIndicator();
		this.remove(landingPage);
		this.add(gameplayPage);
		this.validate();
		this.repaint();
		controller.startNewGame();
	}

	public void updateBoard() {
		System.out.println("Updating board");
		if (lastTurn != gameModel.getLastTurn()) {
			lastTurn = gameModel.getLastTurn();
			this.gameplayPage.getBoard().answer(lastTurn.getCategory());
		}
	}
	
	public void showBoard(){
		gameplayPage.showBoard();
		validate();
		repaint();
	}
	
	public void showWheel(){
		gameplayPage.showWheel();
		validate();
		repaint();
	}

	public void updateScore() {
		gameplayPage.getScoreboard().updateScore(gameModel.getPlayer1Information(), gameModel.getPlayer2Information());
		
	}

	public void updateSpinCount(int spinCount) {
		gameplayPage.updateSpinCount(spinCount);
	}
	
	public void updateActionIndicator(){
		this.gameplayPage.updateActionIndicator(gameModel);
	}

	public void initRoundTwo() {
		gameplayPage.initRoundTwo();
//		this.gameModel = controller.getGame();
//		this.remove(gameplayPage);
//		gameplayPage = new GameplayPage(this, controller);
//		this.add(gameplayPage);
		validate();
		repaint();
	}
}
