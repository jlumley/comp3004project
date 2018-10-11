package core;

import java.util.*;

public class TileRummyMain {
	
	public static final String[] suites = {"R", "B", "G", "Y"};
	public static final int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	ArrayList<Tile> initDeck = new ArrayList<Tile>();
	List<String> initDeckDummy = new ArrayList<String>();
	public Player player0 = new Player();
	public AI player1 = new AI();
	public AI player2 = new AI();
	public AI player3 = new AI();
	
	public void initialize() {
		initDeck = buildDeck(suites,values);
		//initDeckDummy = buildDeck(suites,values);
		//Collections.shuffle(initDeckDummy);
		Collections.shuffle(initDeck);
		dealHands();
	}
	
	//public static List<String> buildDeck(String[] suites, String[] values){
	public static ArrayList<Tile> buildDeck(String[] suites, int[] values){ //when we are done the tile class 
		ArrayList<Tile> dummyDeck = new ArrayList<Tile>();
		//List<String> initDeckDummy2 = new ArrayList<String>();
		for(int x = 0; x < 2; x++) {
			for (int i = 0; i < suites.length; i++){ // for each colour 
				for (int j = 0; j < values.length; j++){ // for each value 
					Tile k = new Tile(suites[i], values[j]); //need the constructor
					//String k = (values[j]) + (suites[i]);
					//initDeckDummy2.add(k); // adding it into the dummy deck 
					dummyDeck.add(k);
				}
			}
		}
		//initDeckDummy2.add("BJ");
		//initDeckDummy2.add("RJ");
		//return initDeckDummy2;
		//TODO Add Wild Tiles
		return dummyDeck;
	}
	
	public void showDeck() {
		for(int i = 0; i < initDeck.size(); i++) {
			System.out.print( initDeck.get(i).toString() + " ");
		}
		System.out.println();
		System.out.println(initDeck.size());
	}
	
	public void dealHands() {
		for(int i = 0; i < 14; i++) {
			player0.drawTile(initDeck);
			player1.drawTile(initDeck);
			player2.drawTile(initDeck);
			player3.drawTile(initDeck);
		}
	}

	public void showHands() {
		
	}
	public void getHand() {
		
	}
}
