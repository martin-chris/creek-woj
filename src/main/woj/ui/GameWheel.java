package main.woj.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Wheel;

public class GameWheel extends JPanel{
	private Wheel wheelModel;
	private JLabel resultsLabel;
	private JButton spinButton;
	private ActionController controller;
	
	//private WheelPanel  wheelWidget;
	private BufferedImage myPicture;
	private BufferedImage rotate;
    private long startTime;
    private JLabel picLabel;
    private double wheelAngle;
    private double acc; 


			
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
		//wheelWidget = new WheelPanel();
		picLabel = new JLabel();
		
		try {
			myPicture = ImageIO.read(new File("lib/wheeloffortune-35133.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		rotateWheel(0);
	
		add(picLabel);
		//add(wheelWidget);
		add(resultsLabel);
		add(spinButton);

	}

	private void setup() {
		this.setSize(this.getMaximumSize());
		//this.setLayout(new GridLayout(1,3));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
	}
	
	private void setupListeners(){
		spinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				animateWheel();
			}
        });
	}
	
	private void spinWheel() {
		this.controller.handleSpin(wheelModel.spin(), wheelModel.getNumSpins());
	}
	
	private void rotateWheel(double angle) {
		
		  double radians = Math.toRadians(angle);
          double sin = Math.abs(Math.sin(45));
          double cos = Math.abs(Math.cos(45));
          int newWidth = (int)Math.round(myPicture.getWidth() * cos + myPicture.getHeight() * sin);
          int newHeight = (int)Math.round(myPicture.getWidth() * sin + myPicture.getHeight() * cos);
		
          rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
          Graphics2D g2d = rotate.createGraphics();
          
          //Graphics2D g2d = myPicture.createGraphics();
          int x = (newWidth - myPicture.getWidth()) / 2;
          int y = (newHeight - myPicture.getHeight()) / 2;
          AffineTransform at = new AffineTransform();
          at.setToRotation(radians, x + (myPicture.getWidth() / 2), y + (myPicture.getHeight() / 2));

          at.translate(x, y);
          g2d.setTransform(at);
          g2d.drawImage(myPicture, 0, 0, this);
          g2d.dispose();
          // Set Label Image icon to new rotated image
  		  picLabel.setIcon(new ImageIcon(rotate));

	}
	
	private void animateWheel() {
        final int RUN_TIME = 5000;

        wheelAngle = 0;
        acc = 0;
        spinButton.setEnabled(false); // Disable just in case we get into a place where we can spin while 
        							  // The wheel is spinning.
        
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long duration = System.currentTimeMillis() - startTime;
                double progress = (double)duration / (double)RUN_TIME;
                if (progress > 1f) {
                    progress = 1f;
                    ((Timer)e.getSource()).stop();
    				spinWheel();
                    spinButton.setEnabled(true);
                }
                rotateWheel(wheelAngle);
                wheelAngle += 15 - acc;
                acc += 0.1;
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        startTime = System.currentTimeMillis();
        timer.start();
        
    
    }
	
//	private class WheelPanel extends JPanel{
//		private Color[] wheelColor;
//
//		public WheelPanel(){
//			wheelColor = new Color[12];
//
//			setup();
//			initComponents();
//		}
//		public void paint(Graphics g) {
//			Graphics2D g2 = (Graphics2D) g;
//			int diameter;
//			
//			//Calc diameter of wheel
//			diameter = getWidth();
//			if(diameter > getHeight())
//			{
//				diameter = getHeight();
//			}
//			//g2.rotate(Math.toRadians(15));
//
//			for(int i = 0; i < 12; i++){
//				g2.setColor(wheelColor[i]);
//			    //g2.fillArc (0, 0, diameter, diameter, 30*i - 15, 30); 
//				g2.fillArc (0, 0, diameter, diameter, -15, 30);
//			    
//			    g2.setColor(Color.BLACK);
//			    g2.drawString("Math", 3*diameter/4, diameter/2);
//			    
//			    //g2.rotate(Math.toRadians(30));
//
//			}
//			g2.setColor(Color.GRAY);
//			g2.fillArc(3*diameter/8, 3*diameter/8, diameter/4, diameter/4, 0, 360);
//			
//
//
//	    }	
//
//
//		private void setup() {	
//
//			wheelColor[0] = Color.YELLOW;
//			wheelColor[1] = Color.GREEN;
//			wheelColor[2] = Color.CYAN;
//			wheelColor[3] = Color.ORANGE;
//			wheelColor[4] = Color.PINK;
//			wheelColor[5] = Color.RED;
//			wheelColor[6] = Color.YELLOW;
//			wheelColor[7] = Color.GREEN;
//			wheelColor[8] = Color.CYAN;
//			wheelColor[9] = Color.ORANGE;
//			wheelColor[10] = Color.PINK;
//			wheelColor[11] = Color.RED;
//			
//			this.getMaximumSize();
//		}
//
//		private void initComponents() {
//		}
//
//		private void setupListeners(){
//		}
//	}
}
