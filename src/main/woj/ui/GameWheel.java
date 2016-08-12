package main.woj.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


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
	private WheelPanel  wheelWidget;
			
	public GameWheel(ActionController controller){
		this.controller = controller;
		this.wheelModel = new Wheel();

		setup();
		initComponents();
		setupListeners();
	}
	
	
	private void initComponents() {
		resultsLabel = new JLabel("");
		spinButton = new JButton("Spin Wheel");
		wheelWidget = new WheelPanel();
		add(wheelWidget);
		add(resultsLabel);
		add(spinButton);

	}

	private void setup() {
		this.setSize(this.getMaximumSize());
		this.setLayout(new GridLayout(1,1));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
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
	
	
	private class WheelPanel extends JPanel{
		private Color[] wheelColor;

		public WheelPanel(){
			wheelColor = new Color[12];

			setup();
			initComponents();
		}
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			//g2.rotate(Math.toRadians(15));

			for(int i = 0; i < 12; i++){
				g2.setColor(wheelColor[i]);
			    g2.fillArc (0, 0, getWidth(), getHeight(), 30*i, 30); 
			}
			g2.setColor(Color.GRAY);
			g2.fillArc(3*getWidth()/8, 3*getHeight()/8, getWidth()/4, getHeight()/4, 0, 360);

	    }	


		private void setup() {	

			wheelColor[0] = Color.YELLOW;
			wheelColor[1] = Color.GREEN;
			wheelColor[2] = Color.CYAN;
			wheelColor[3] = Color.ORANGE;
			wheelColor[4] = Color.PINK;
			wheelColor[5] = Color.RED;
			wheelColor[6] = Color.YELLOW;
			wheelColor[7] = Color.GREEN;
			wheelColor[8] = Color.CYAN;
			wheelColor[9] = Color.ORANGE;
			wheelColor[10] = Color.PINK;
			wheelColor[11] = Color.RED;
			
			this.getMaximumSize();
		}

		private void initComponents() {
		}

		private void setupListeners(){
		}
	}
}
