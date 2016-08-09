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
                "ham");
		return result;
	}
}
