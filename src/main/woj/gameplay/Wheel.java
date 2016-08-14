package main.woj.gameplay;

import java.util.ArrayList;
import java.util.Random;

public class Wheel {
	private Random random;
	private int spinCount;

	public enum StaticCategory {
		CATEGORY_1,
		CATEGORY_2,
		CATEGORY_3,
		CATEGORY_4,
		CATEGORY_5,
		CATEGORY_6,
		LOSE_TURN,
		FREE_TURN,
		BANKRUPT,
		PLAYER_CHOICE,
		OPPONENT_CHOICE,
		SPIN_AGAIN
	}
	
	public Wheel(){
		spinCount = 0;
		this.random = new Random();
	}
	
	public StaticCategory spin(){
		spinCount++;
		return getRandomCategory();
	}
	
	private StaticCategory getRandomCategory(){
		return StaticCategory.values()[generateSpin()];
	}
	
	private int generateSpin(){
		return random.nextInt(StaticCategory.values().length-6);
	}

	public int getNumSpins() {
		return spinCount;
	}
}
