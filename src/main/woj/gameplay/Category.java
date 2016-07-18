package main.woj.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
	public QuestionCollection questionSet;
	public String title;

	public Category(String title, QuestionCollection questions){
		this.title = title;
		this.questionSet = questions;
	}
	
	public QuestionCollection questions(){
		return questionSet;
	}
	
	public String title(){
		return title;
	}
	
	public String toString(){
		String returnVal = title() + "\n";
		for(int i=0;i<questionSet.length();i++){
			returnVal += questionSet.get(i).toString() + "\n\n";
		}
		return returnVal;
	}
	
	public static Category build(HashMap<String, Object> questionSet){
		String title = (String) questionSet.get("title");
		QuestionCollection questions = QuestionCollection.build((ArrayList<HashMap<String, Object>>) questionSet.get("questions"));
		return new Category(title, questions);
	}
}
