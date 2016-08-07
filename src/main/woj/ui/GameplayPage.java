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
	private GameWheel wheel;
	private ScoreBoard scoreBoard;
	private QuestionPanel questionPanel;
	private ActionIndicator actionIndicator;
	private GameBoard board;
	private GameFrame gameFrame;
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
		wheel = new GameWheel(controller);
		board = new GameBoard(controller);
		scoreBoard = new ScoreBoard();
		actionIndicator = new ActionIndicator();
		scoreBoard = new ScoreBoard();
		this.add(board, BorderLayout.CENTER);
		this.add(bottomPanel(), BorderLayout.SOUTH);
	}
	
	public GameBoard getBoard(){
		return board;
	}
	
	private JPanel bottomPanel(){
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.add(scoreBoard);
		bottomPanel.add(actionIndicator);
		bottomPanel.add(wheel);
		return bottomPanel;
	}
	
}
