import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Category;
import main.woj.gameplay.Game;
import main.woj.utils.QuestionDeserializer;

import com.esotericsoftware.yamlbeans.YamlException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, YamlException {
		ActionController game = new ActionController("lib/question_set.example.yml");
	}
}
