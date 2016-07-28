package main.woj.gameplay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.naming.OperationNotSupportedException;

public class QuestionCollection{
	private ArrayList<Question> questions;
	private int current;

	public QuestionCollection(){
		this.questions = new ArrayList<Question>();
		this.current = 0;
	}
	
	public ArrayList<Question> getQuestionsByDifficulty(){
		Collections.sort(questions);
		return questions;
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
	
	public boolean hasNext() {
	    return current < questions.size();
	}

	public Question next() {
        if (! hasNext())   throw new NoSuchElementException();
        return questions.get(current++);
	}
}
