package main.woj.gameplay;
import java.util.ArrayList;
import java.util.HashMap;

public class Category {
	public QuestionCollection questionCollection;
	public String title;

	public Category(String title, QuestionCollection questions){
		this.title = title;
		this.questionCollection = questions;
	}
	
	public QuestionCollection questions(){
		return questionCollection;
	}
	
	public String title(){
		return title;
	}
	
	public String toString(){
		String returnVal = "Category: " + title() + "\n";
		for(int i=0;i<questionCollection.length();i++){
			returnVal += questionCollection.get(i).toString() + "\n\n";
		}
		return returnVal;
	}
	
	public static Category build(HashMap<String, Object> questionSet){
		String title = (String) questionSet.get("title");
		QuestionCollection questions = QuestionCollection.build((ArrayList<HashMap<String, Object>>) questionSet.get("questions"));
		return new Category(title, questions);
	}

	public Question nextQuestion(){
		return questionCollection.next();
	}
	
	public boolean hasNext(){
		return questionCollection.hasNext();
	}
}
