package main.woj.ui;

import javax.swing.JPanel;

import main.woj.gameplay.Question;

public class QuestionPanel extends JPanel {
	private Question question;
	public QuestionPanel(Question question){
		this.question = question;
	}
}
