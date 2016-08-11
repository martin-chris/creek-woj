package main.woj.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.woj.gameplay.Question;

public class QuestionDialog {
	public static String showQuestion(JFrame gameFrame, Question question){
		String result = (String)JOptionPane.showInputDialog(
				gameFrame,
				question.ask(),
                question.getQuestion(),
                JOptionPane.PLAIN_MESSAGE,
                null,
                question.getAnswerKey(),
  //              "ham");
		// return result;
		 "NOANSWER");
		
		//This code prevents the cancel button from breaking.
		//If the cancel button is selected, the player will have answered incorrectly.
		if (result != null)
			return result;
		else
			return "NOANSWER";
	}
}
