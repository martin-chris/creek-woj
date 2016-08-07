package main.woj.ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.woj.controllers.ActionController;
import main.woj.gameplay.Board;
import main.woj.gameplay.Category;

public class GameBoard extends JPanel{
	private final int CATEGORIES_COUNT = 5;
	private final int BASE_MULTIPLIER = 200;
	private Board boardModel;
	private ActionController controller;
	private HashMap<Category, BoardCategory> categoryMap;
	
	public GameBoard(ActionController controller){
		this.controller = controller;
		this.boardModel = controller.getBoard();
		categoryMap = new HashMap<Category, BoardCategory>();
		setup();
		populateBoard();
		repaint();
	}
	
	public void answer(Category category){
		categoryMap.get(category).answer();
	}

	private void setup() {
		this.setSize(this.getMaximumSize());
		this.setLayout(new GridLayout(1,6));
	}
	
	private void populateBoard(){
		for (Category category : boardModel.getCategories()){
			BoardCategory currentCategory = new BoardCategory(category);
			this.categoryMap.put(category, currentCategory);
			this.add(currentCategory);
		}
	}
	
	private class BoardCategory extends JPanel{
		private Category category;
		ArrayList<JButton> questions = new ArrayList<JButton>();
		int answered = 0;
		
		public BoardCategory(Category category){
			this.category = category;
			this.setup();
			this.initComponents();
		}
		
		public void answer(){
			questions.get(answered).setEnabled(false);
			answered++;
		}

		private void initComponents() {
			add(new JButton(category.title()));
			for(int i = 0; i < CATEGORIES_COUNT; i++){
				int value = category.questions().get(i).value(BASE_MULTIPLIER);
				add(createNewButton(value));
			}
		}
		
		private JButton createNewButton(int value){
			JButton button = new JButton("$" + value );
			questions.add(button);
			return button;
		}

		private void setup() {
			setLayout(new GridLayout(6,1));
		}
	}
	
	

}
