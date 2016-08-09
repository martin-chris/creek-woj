package main.woj.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Board;
import main.woj.gameplay.Game;

public class GameplayPage extends JPanel{
	private ScoreBoard scoreBoard;
	private QuestionPanel questionPanel;
	private ActionIndicator actionIndicator;
	private GameFrame gameFrame;
	private GameplayContainer gameplayContainer;
	private ActionController controller;
	
	public GameplayPage(GameFrame gameFrame, ActionController controller){
		this.gameFrame = gameFrame;
		this.controller = controller;
		setup();
		initComponents();
	}

	private void setup() {
		this.setSize(getMaximumSize());
		this.setLayout(new BorderLayout());
	}

	private void initComponents() {
		gameplayContainer = new GameplayContainer(controller);
		scoreBoard = new ScoreBoard();
		actionIndicator = new ActionIndicator();
		scoreBoard = new ScoreBoard();
		this.add(gameplayContainer, BorderLayout.CENTER);
		this.add(bottomPanel(), BorderLayout.SOUTH);
	}
	
	public void showBoard(){
		gameplayContainer.switchToBoard();
	}
	
	public void showWheel(){
		gameplayContainer.switchToWheel();
	}
	
	public GameBoard getBoard(){
		return gameplayContainer.getBoard();
	}
	
	private JPanel bottomPanel(){
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));
		bottomPanel.add(scoreBoard);
		bottomPanel.add(actionIndicator);
		return bottomPanel;
	}
	
}
