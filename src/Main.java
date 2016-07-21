import java.io.FileNotFoundException;
import java.util.ArrayList;
import main.woj.gameplay.Category;
import main.woj.utils.QuestionDeserializer;
import com.esotericsoftware.yamlbeans.YamlException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, YamlException {
		ArrayList<Category> categories = QuestionDeserializer.importQuestionSet("lib/question_set.example.yml");
		
		for(Category category : categories){
			System.out.println(category.toString());
		}
	}
}
