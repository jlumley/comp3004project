package core;

import java.util.ArrayList;

//public class Strategy3 extends Player implements AIStrategy, Subject{
public class Strategy3 implements AIStrategy{
	//private ArrayList observers = new ArrayList();
	
	public void playTurn() {
		//Check for 30 point limit and play it
		
		//Check if can win this turn, if so Win
		
		//(Observer) Ask game for the Hand sizes of all other players
	
		//If This has +3 than any of them: Play all Tiles possible
		//Else: keep all in-hand melds, and try adding to existing melds on board
	}
	
	/*
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}*/
}
