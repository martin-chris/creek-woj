package main.woj.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.woj.gameplay.Game;
import main.woj.gameplay.Player;

public class ActionIndicator extends JPanel{
	private JLabel label;
	public ActionIndicator(){
		setup();
		initComponents();
	}
	
	private void initComponents(){
		label = new JLabel("Action Indicator");
		add(label);
	}
	
	private void setup() {
		this.setSize(this.getMaximumSize());
		this.setLayout(new GridLayout(1,1));
	}
	
	public void updateDisplay(Game gameModel){
		Player currentPlayer = gameModel.getCurrentPlayer();
		label.setText(currentPlayer.getName() + "'s turn");
		this.validate();
		this.repaint();
	}
}
