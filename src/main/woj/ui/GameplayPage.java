package main.woj.ui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class GameplayPage extends JPanel{
	private Wheel wheel;
	private ScoreBoard scoreBoard;
	private QuestionPanel questionPanel;
	private ActionIndicator actionIndicator;
	private GameBoard board;
	
	public GameplayPage(){
		setup();
		initComponents();
	}

	private void setup() {
		this.setSize(getMaximumSize());

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	private void initComponents() {
		wheel = new Wheel();
		board = new GameBoard();
		scoreBoard = new ScoreBoard();
		actionIndicator = new ActionIndicator();
		scoreBoard = new ScoreBoard();
		this.add(board);
		this.add(bottomPanel());
	}
	
	private JPanel bottomPanel(){
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(scoreBoard);
		bottomPanel.add(actionIndicator);
		bottomPanel.add(wheel);
		return bottomPanel;
	}
	
}
