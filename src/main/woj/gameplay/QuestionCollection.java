package main.woj.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionCollection {
	ArrayList<Question> questions;

	public QuestionCollection(){
		this.questions = new ArrayList<Question>();
	}
	
	public int length(){
		return questions.size();
	}
	
	public void add(Question question){
		questions.add(question);
	}
	
	public Question get(int index){
		return this.questions.get(index);
	}

	public static QuestionCollection build(ArrayList<HashMap<String, Object>> questions) {
		QuestionCollection questionList = new QuestionCollection(); 
		for(int i = 0; i<questions.size();i++){
			questionList.add(Question.build(questions.get(i)));
		}
		return questionList;
	}
}
