package main.woj.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBoard extends JPanel{
	private JLabel label;
	public ScoreBoard(){
		setup();
		initComponents();
	}
	
	private void initComponents(){
		label = new JLabel("Scoreboard");
		add(label);
	}
	
	private void setup() {
		this.setSize(this.getMaximumSize());
		this.setLayout(new GridLayout(1,1));
	}
}


