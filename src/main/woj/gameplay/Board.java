package main.woj.gameplay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Board {
	private HashMap<String, Category> categoryMap;
	
	public Board(ArrayList<Category> categories){
		categoryMap = new HashMap<String, Category>();
		for (Category category : categories){
			categoryMap.put(category.title(), category);
		}
	}
	
	public Boolean isComplete(){
		for(Category category : getCategories()){
			if (category.hasNext()){
				return false;
			}
				
		}
		return true;
	}

	public Collection<Category> getCategories() {
		return categoryMap.values();
	}
	
	public Category getCategory(String key){
		return categoryMap.get(key);
	}
	
	public Boolean hasCategory(String key){
		return categoryMap.containsKey(key);
	}	
	
	public Question getNextQuestion(String key){
		return categoryMap.get(key).nextQuestion();
	}
	
	public Boolean hasNextQuestion(String key){
		return categoryMap.get(key).hasNext();
	}
	
}
