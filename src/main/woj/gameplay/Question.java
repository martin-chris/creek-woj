package main.woj.gameplay;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Question implements Comparable<Question> {
	private String question;
	private Map<String,String> answers;
	private String answer;
	private int difficulty;

	public Question(String question, Map<String, String> answers, String answer, String difficulty){
		this.question = question;
		this.answers = answers;
		this.answer = answer;
		this.difficulty = Integer.parseInt(difficulty);
	}
	
	private SortedSet<String> sortedAnswerKeys(){
		SortedSet<String> set = new TreeSet<String>(answers.keySet());
		return set;
	}

	public String getQuestion(){
		return question;
	}
	
	public Object[] getAnswerKey(){
		return answers.keySet().toArray();
	}
	
	public int value(int multiplier){
		return difficulty * multiplier;
	}
	
	public int getDifficulty(){
		return difficulty;
	}
	
	public Boolean verifyAnswer(String answer){
		return this.answer.toLowerCase().equals(answer.toLowerCase());
	}
	
	public String getAnswer(){
		return answers.get(answer);
	}
	
	public String ask(){
		return question + possibleAnswers();
	}
	
	public String possibleAnswers(){
		String possibleAnswers = "";
		for(String key : sortedAnswerKeys()){
			possibleAnswers += "\n" + key + ": " + answers.get(key);
		}
		return possibleAnswers;
	}
	
	public String toString(){
		String output = question + " (difficulty: " + getDifficulty() + ") \n";
		output += possibleAnswers();
		output += "Answer: " + answer + " (" + getAnswer() + ")";
		return output;
	}

	public static Question build(HashMap<String, Object> questionData) {
		String question = (String) questionData.get("question");
		Map answers = (Map) questionData.get("answers");
		String answer = (String) questionData.get("answer");
		String difficulty = (String) questionData.get("difficulty");
		return new Question(question, answers, answer, difficulty);
	}

	@Override
	public int compareTo(Question q1) {
		return this.getDifficulty() - q1.getDifficulty();
	}
}
