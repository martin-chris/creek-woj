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
	private Game gameModel;
	private ActionController controller;

	public GameFrame(ActionController controller){
		super("Wheel of Jeopardy");
		this.controller = controller;
		this.gameModel = controller.getGame();
		initComponents();
		setup();
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
		this.remove(landingPage);
		this.add(gameplayPage);
		this.validate();
		this.repaint();
		controller.startNewGame();
	}

	public void updateBoard(Turn lastTurn) {
		this.gameplayPage.getBoard().answer(lastTurn.getCategory());
		this.repaint();
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
}
