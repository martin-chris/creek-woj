package main.woj.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import main.woj.controllers.ActionController;

public class GameplayContainer extends JPanel {
	private GameWheel wheel;
	private GameBoard board;
	private ActionController controller;
	private GameFrame frame;
	public GameplayContainer(GameFrame frame, ActionController controller){
		this.frame = frame;
		this.controller = controller;
		setup();
		initComponents();
	}
	private void initComponents() {
		wheel = new GameWheel(controller);
		board = new GameBoard(controller);
		this.add(board);	
	}

	private void setup() {		
		int gameWidth = (int) (Math.round(frame.getWidth()));
		int gameHeight = (int) (Math.round(frame.getHeight() * 0.70));
		this.setPreferredSize(new Dimension(gameWidth, gameHeight));
		this.setLayout(new GridLayout(1,1));
	}
	
	public GameBoard getBoard(){
		return board;
	}
	
	public void switchToWheel(){		
		this.remove(board);
		this.add(wheel);
	}
	
	public void switchToBoard(){
		this.remove(wheel);
		this.add(board);
	}
	public void initRoundtwo() {
		board.initRoundTwo();
	}
}
