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
import main.woj.gameplay.Wheel.StaticCategory;


public class GameWheel extends JPanel{
	private Wheel wheelModel;
	private JLabel resultsLabel;
	private JButton spinButton;
	private ActionController controller;
	
	private BufferedImage myPicture;
	private BufferedImage rotate;
    private long startTime;
    private JLabel picLabel;
    private double wheelAngle;
    private double acc; 
    private StaticCategory spinResult; 
    int lastSpin = 0; 



			
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
		picLabel = new JLabel();
		JLabel arrowLabel = new JLabel();

		
		try {
			//myPicture = ImageIO.read(new File("lib/wheeloffortune-35133.png"));
			myPicture = ImageIO.read(new File("lib/tce_woj_wheel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		rotateWheel(0);
	
		add(picLabel);

		BufferedImage arrow;
		try {
			arrow = ImageIO.read(new File("lib/arrow.png"));
			add(arrowLabel);
			arrowLabel.setIcon(new ImageIcon(arrow));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		add(resultsLabel);
		add(spinButton);

	}

	private void setup() {
		this.setSize(this.getMaximumSize());
		//this.setLayout(new GridLayout(1,3));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		drawWheel();
	}
	
	private void setupListeners(){
		spinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				spinResult = wheelModel.spin();
				animateWheel();
			}
        });
	}
	
	private void spinWheel() {
		this.controller.handleSpin(spinResult, wheelModel.getNumSpins());
	}
	
	private void drawWheel() {
		int diameter = 380;
		int angle = 30;
        Color[] wheelColor = new Color[12];
		
		wheelColor[0] = Color.YELLOW;
		wheelColor[1] = Color.BLACK; //Bankrupt
		wheelColor[2] = Color.GREEN;
		wheelColor[3] = Color.WHITE; //Free Turn
		wheelColor[4] = Color.CYAN;
		wheelColor[5] = Color.BLACK; //Opponent's Choice
		wheelColor[6] = new Color(171,121,219); //Purple
		wheelColor[7] = Color.WHITE; // Player's Choice
		wheelColor[8] = Color.PINK; 
		wheelColor[9] = Color.BLACK; // Lose Turn
		wheelColor[10] = Color.RED;
		wheelColor[11] = Color.WHITE; //Spin Again
		
		String[] sliceText = new String[12];
		
		sliceText[10] = this.controller.gameModel.getCategoryKey(StaticCategory.values()[0]); //"Cat 1";
		sliceText[11] = "Bankrupt!";
		sliceText[0] = this.controller.gameModel.getCategoryKey(StaticCategory.values()[1]); //"Cat 2";
		sliceText[1] = "Free Turn!";
		sliceText[2] = this.controller.gameModel.getCategoryKey(StaticCategory.values()[2]); //"Cat 3";
		sliceText[3] = "Opponent's!";
		sliceText[4] = this.controller.gameModel.getCategoryKey(StaticCategory.values()[3]); //"Cat 4";
		sliceText[5] = "Player's!";
		sliceText[6] = this.controller.gameModel.getCategoryKey(StaticCategory.values()[4]); //"Cat 5";
		sliceText[7] = "Lose Turn!";
		sliceText[8] = this.controller.gameModel.getCategoryKey(StaticCategory.values()[5]); //"Cat 6";
		sliceText[9] = "Spin Again!";

		//Limit each string
		for(int i = 0; i < 12; i++) {
	      if(sliceText[i].length() > 14) {
		    sliceText[i] = sliceText[i].substring(0, 13);
	      }
		}
		
		
  		BufferedImage wheelImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = wheelImage.createGraphics();

        // Create the 12 segments
		for(int i = 0; i < 12; i++){
			//g2.setColor(wheelColor[i]);
	        GradientPaint gp = new GradientPaint(diameter/2, diameter/2, wheelColor[i], 
	        		(int)Math.round(diameter/2 + diameter * Math.sin(30*i)), 
	        		(int)Math.round(diameter/2 + diameter * Math.cos(30*i)), 
	        		Color.GRAY, true);
	        g2.setPaint(gp);
		    g2.fillArc (0, 0, diameter, diameter, 30*i - 12, 30); 
			//g2.fillArc (0, 0, diameter, diameter, -15, 30);
		}
		
		//Draw Center Circle
		g2.setColor(new Color(43,147,119));
		g2.fillArc(3*diameter/8, 3*diameter/8, diameter/4, diameter/4, 0, 360);

		//Add text while rotating
	    double radians = Math.toRadians(30);

	    rotate = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotate.createGraphics();
	    g2d.drawImage(wheelImage, 0, 0, this);
	    AffineTransform at = new AffineTransform();
        //Font f = new Font("Impact", Font.PLAIN, 14);
        Font f = new Font("Cooper Black", Font.PLAIN, 16);

	    g2d.setFont(f);
        
		for(int i = 0; i < 12; i++) {
		    at.setToRotation(radians*i, (diameter / 2), (diameter / 2));
		    g2d.setTransform(at);
		    
			if((i%4) == 3) {
		      g2d.setColor(Color.WHITE);
			} else {
			  g2d.setColor(Color.BLACK);
			}
		    g2d.drawString(sliceText[i], 11*diameter/16, diameter/2);

		}

	    
		//Save image
		File outputfile = new File("lib/tce_woj_wheel.png");
		try {
			//ImageIO.write(wheelImage, "png", outputfile);	
			ImageIO.write(rotate, "png", outputfile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//discard graphics resources
	    g2d.dispose();		
	    g2.dispose();
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
        //final int RUN_TIME = 5000;

        wheelAngle = 0;
        acc = 0;
        spinButton.setEnabled(false); // Disable just in case we get into a place where we can spin while 
        							  // The wheel is spinning.
        

        if(spinResult == StaticCategory.CATEGORY_1) {
        	wheelAngle = 0;
        }
        if(spinResult == StaticCategory.BANKRUPT) {
        	wheelAngle = -30;
        }  
        if(spinResult == StaticCategory.CATEGORY_2) {
        	wheelAngle = -60;
        } 
        if(spinResult == StaticCategory.FREE_TURN) {
        	wheelAngle = -90;
        } 
        if(spinResult == StaticCategory.CATEGORY_3) {
        	wheelAngle = -120;
        } 
        if(spinResult == StaticCategory.OPPONENT_CHOICE) {
        	wheelAngle = -150;
        } 
        if(spinResult == StaticCategory.CATEGORY_4) {
        	wheelAngle = -180;
        }
        if(spinResult == StaticCategory.PLAYER_CHOICE) {
        	wheelAngle = -210;
        }
        if(spinResult == StaticCategory.CATEGORY_5) {
        	wheelAngle = -240;
        }
        if(spinResult == StaticCategory.LOSE_TURN) {
        	wheelAngle = -270;
        }
        if(spinResult == StaticCategory.CATEGORY_6) {
        	wheelAngle = -300;
        }
        if(spinResult == StaticCategory.SPIN_AGAIN) {
        	wheelAngle = -330;
        }
 
        Timer timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {            	
            	if(acc == 15) {
                  ((Timer)e.getSource()).stop();
    			  spinWheel();
                  spinButton.setEnabled(true);
            	}
                rotateWheel(wheelAngle);
                wheelAngle += 15 - acc;
                acc += 0.1;
                if(acc > 15) acc = 15;
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        startTime = System.currentTimeMillis();
        timer.start();
        
    
    }
	
}
