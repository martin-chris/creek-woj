package main.woj.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import main.woj.controllers.ActionController;

public class GameplayContainer extends JPanel {
	private GameWheel wheel;
	private GameBoard board;
	private ActionController controller;
	public GameplayContainer(ActionController controller){
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
		this.setSize(this.getMaximumSize());
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
}
