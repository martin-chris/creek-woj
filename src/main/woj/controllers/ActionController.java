package main.woj.controllers;

import java.util.Observable;
import java.util.Observer;

import main.woj.gameplay.Game;
import main.woj.ui.GameFrame;

public class ActionController implements Observer {
	private Game gameModel;
	private GameFrame gameFrame;
	public ActionController(){
		gameModel = new Game("lib/question_set.example.yml");
		gameFrame = new GameFrame();
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
	
}
