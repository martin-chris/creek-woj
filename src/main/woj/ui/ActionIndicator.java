package main.woj.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
}
