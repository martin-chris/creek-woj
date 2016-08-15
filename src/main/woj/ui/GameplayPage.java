package main.woj.ui;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Game;

public class GameplayPage extends JPanel{
	private ScoreBoard scoreBoard;
	private QuestionPanel questionPanel;
	private ActionIndicator actionIndicator;
	private GameFrame gameFrame;
	private GameplayContainer gameplayContainer;
	private ActionController controller;
	private JLabel spinLabel;
	
	public GameplayPage(GameFrame gameFrame, ActionController controller){
		this.gameFrame = gameFrame;
		this.controller = controller;
		setup();
		initComponents();
	}
	
	private void setup() {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}

	private void initComponents() {
		gameplayContainer = new GameplayContainer(gameFrame, controller);
		scoreBoard = new ScoreBoard();
		spinLabel = new JLabel("");
		actionIndicator = new ActionIndicator();
		this.add(gameplayContainer);
		this.add(bottomPanel());
		updateSpinCount(0);
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

		bottomPanel.setLayout(new GridLayout(1,3));
		
		bottomPanel.add(scoreBoard);
		bottomPanel.add(spinLabel);
		bottomPanel.add(actionIndicator);
		return bottomPanel;
	}

	public ScoreBoard getScoreboard() {
		return scoreBoard;
	}
	
	public void updateSpinCount(int count){
		spinLabel.setText("Spins: " + count);

	}

	public void updateActionIndicator(Game gameModel) {
		actionIndicator.updateDisplay(gameModel);
	}

	public void initRoundTwo() {
		gameplayContainer.initRoundtwo();
	}
	
}
