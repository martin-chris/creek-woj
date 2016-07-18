package main.woj.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import main.woj.gameplay.Category;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class QuestionDeserializer {
	
	public static ArrayList<Category> importQuestionSet(String filename) throws YamlException, FileNotFoundException{
	    YamlReader reader = new YamlReader(new FileReader(filename));
	    ArrayList<HashMap<String, Object>> categoryList = (ArrayList<HashMap<String, Object>>) ((HashMap)reader.read()).get("game");
	    return buildQuestionSet(categoryList);
	}
	
	private static ArrayList<Category> buildQuestionSet(ArrayList<HashMap<String, Object>> questionSet){
		ArrayList<Category> categorySet = new ArrayList<Category>();
		for(int i=0;i< questionSet.size();i++){
			categorySet.add(Category.build(questionSet.get(i)));
		}
		return categorySet;
	}
}

