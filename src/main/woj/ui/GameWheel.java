package main.woj.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Wheel;

public class GameWheel extends JPanel{
	private Wheel wheelModel;
	private JLabel resultsLabel;
	private JButton spinButton;
	private ActionController controller;
	public GameWheel(ActionController controller){
		this.controller = controller;
		this.wheelModel = new Wheel();
		setup();
		initComponents();
		setupListeners();
	}
	
	private void initComponents() {
		resultsLabel = new JLabel("");
		spinButton = new JButton("Spin");
		add(resultsLabel);
		add(spinButton);
	}

	private void setup() {
		this.setSize(this.getMaximumSize());
		this.setLayout(new GridLayout(2,1));
		setBorder(new EmptyBorder(200, 200, 200, 200));
	}
	
	private void setupListeners(){
		spinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				spinWheel();
			}
        });
	}
	
	private void spinWheel() {
		this.controller.startNewTurn(wheelModel.spin());
	}
}
