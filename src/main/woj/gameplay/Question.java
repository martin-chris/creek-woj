package main.woj.gameplay;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Question {
	String question;
	Map<String,String> answers;
	String answer;

	public Question(String question, Map<String, String> answers, String answer){
		this.question = question;
		this.answers = answers;
		this.answer = answer;
	}
	
	private SortedSet<String> sortedAnswerKeys(){
		SortedSet<String> set = new TreeSet<String>(answers.keySet());
		return set;
	}
	
	public String getAnswer(){
		return answers.get(answer);
	}
	
	public String toString(){
		String output = question + "\n";
		for(String key : answers.keySet()){
			output += key + ": " + answers.get(key) + "\n";
		}
		output += "Answer: " + answer + " (" + getAnswer() + ")";
		return output;
	}

	public static Question build(HashMap<String, Object> questionData) {
		String question = (String) questionData.get("question");
		Map answers = (Map) questionData.get("answers");
		String answer = (String) questionData.get("answer");
		return new Question(question, answers, answer);
	}
}
