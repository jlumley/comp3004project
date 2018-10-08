package core;

import java.util.*;

public class TileRummyMain {
	
	public static final String[] suites = {"R", "B", "G", "Y"};
	public static final String[] values = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
	List<Tile> initDeck = new ArrayList<Tile>();
	List<String> initDeckDummy = new ArrayList<String>();
	
	public void initialize() {
		//initDeck = buildDeck(suites,values);
		initDeckDummy = buildDeck(suites,values);
		Collections.shuffle(initDeckDummy);
	}
	
	public static List<String> buildDeck(String[] suites, String[] values){
	//public static List<Tile> buildDeck(String[] suites, String[] values){ when we are done the tile class 
		//List<Tile> dummyDeck = new ArrayList<Tile>();
		List<String> initDeckDummy2 = new ArrayList<String>();
		for(int x = 0; x < 2; x++) {
			for (int i = 0; i < suites.length; i++){ // for each colour 
				for (int j = 0; j < values.length; j++){ // for each value 
					//Tile k = new Tile(values[j], suites[i],inputValue); //need the constructor
					String k = (values[j]) + (suites[i]);
					initDeckDummy2.add(k); // adding it into the dummy deck 
				}
			}
		}
		initDeckDummy2.add("BJ");
		initDeckDummy2.add("RJ");
		return initDeckDummy2;
	}
	
	public void showDeck() {
		for(int i = 0; i < initDeckDummy.size(); i++) {
			System.out.print( (initDeckDummy.get(i)) + " ");
		}
		System.out.println();
		System.out.println(initDeckDummy.size());
	}

	public void showHands() {
		
	}
	public void getHand() {
		
	}
}
