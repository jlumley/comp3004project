package core;

import java.util.*;

public class TileRummyMain{
	
	public static final String[] suites = {"R", "B", "G", "O"};
	public static final int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	public static ArrayList<ArrayList<Tile>> field = new ArrayList<ArrayList<Tile>>();
	ArrayList<Tile> initDeck = new ArrayList<Tile>();
	List<String> initDeckDummy = new ArrayList<String>();
	public static Player player0 = new Player();
	public static AI player1 = new AI(new Strategy1());
	public static AI player2 = new AI(new Strategy2());
	public static AI player3 = new AI(new Strategy3());
	boolean gameStatus = true;
	public int playerTurn = 0;
	public static int fieldSize = 0;
	private GUI gui;
	private boolean guiMode = false;

	public boolean isGUI()
	{
		return guiMode;
	}
	
	public void setGUI(GUI tempGUI)
	{
		guiMode = true;
		gui = tempGUI;
	}
	
	public void initialize() {
		resetStaticVars();
		initDeck = buildDeck(suites,values);
		Collections.shuffle(initDeck);
		dealHands();
	}
	
	public static ArrayList<Tile> buildDeck(String[] suites, int[] values){ //when we are done the tile class 
		ArrayList<Tile> dummyDeck = new ArrayList<Tile>();
		for(int x = 0; x < 2; x++) {
			for (int i = 0; i < suites.length; i++){ // for each colour 
				for (int j = 0; j < values.length; j++){ // for each value 
					Tile k = new Tile(suites[i], values[j]); 
					dummyDeck.add(k);
				}
			}
		}
		//TODO Add Wild Tiles
		return dummyDeck;
	}
	
	public void showDeck() {
		System.out.println();
		for(int i = 0; i < initDeck.size(); i++) {
			System.out.print( initDeck.get(i).toString() + " ");
		}
		System.out.println();
		System.out.println(initDeck.size());
		if(gui != null) //Make sure gui is set then update tiles
			gui.updateTiles();
		if(isGUI() == true)
			GUI.sayMsg("Building deck");
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
		GUI.sayMsg("User's 1 Hand: Show Hand" + player1.getHand());
		System.out.println("User's 1 Hand: " + player1.getHand() );
		if(isGUI() == true) //Make sure gui is set then update tiles
			gui.updateTiles();
	}
	public void getHandSize() { 
		
	}
	public void showField() {
		if(field.size() > 0) {
			for(int x = 0; x < field.size(); x++) { // gets the length of the first object in the first position
				System.out.print(field.get(x) + ",");
			}
			System.out.println(); //spacing
		}else {
			System.out.println("field is empty");
		}
		
		if(isGUI() == true) //Make sure gui is set then update tiles
			gui.updateTiles();
	}
/*
	public boolean checkMend(ArrayList<Tile> collection){ // checks for users first hand with sets 
		// move this to player after team meeting
		boolean returnV = true;
		int checkSum = 0;
		ArrayList<String> suitDeck = new ArrayList<String>();
		int checkValue = collection.get(0).getValue();
		if(collection.size() == 0) {
			return false;
		}else {
			for(int i = 0; i < collection.size(); i++) {
				if(!suitDeck.contains(collection.get(i).getColour())) {
					if(collection.get(i).getValue() == checkValue){
						suitDeck.add(collection.get(i).getColour());
						checkSum += collection.get(i).getValue();
					}
				}
			}
		}
		System.out.println(checkSum + " " + suitDeck + " " + checkValue);
		if(checkSum < 30) {
			returnV = false;
		}
		if(returnV) {
			System.out.println(checkSum);
			addMend(collection);
		}
		return returnV;
	}
*/
	
	public static void addMend(ArrayList<Tile> collection) { // basic adding into the field of tiles
		System.out.println("Size: " + collection.size());
		field.add(collection);
		System.out.println("Add to Field" + field.get(fieldSize));
		fieldSize++;
		
	}
	
	

	public boolean checkGameStatus() {
		if(player0.getHand().size() == 0) {
			gameStatus = false;
			System.out.println("Player has won!");
			if(isGUI() == true)
				GUI.sayMsg("Player has won!");
		}else if(player1.getHand().size() == 0) {
			if(isGUI() == true)
				gameStatus = false;
			System.out.println("User 2 has won!");
			GUI.sayMsg("User 2 has won!");
		}else if(player2.getHand().size() == 0) {
			if(isGUI() == true)
				gameStatus = false;
			System.out.println("User 3 has won!");
			GUI.sayMsg("User 3 has won!");
		}else if(player3.getHand().size() == 0) {
			if(isGUI() == true)
				gameStatus = false;
			System.out.println("User 4 has won!");
			GUI.sayMsg("User 4 has won!");
		}
		return gameStatus;
	}

	public void playGame() { // where most of the game logic is going to go.
		while(gameStatus){
			System.out.print(playerTurn + " ");
			
			if(playerTurn == 0) {
				System.out.println("Players Turn");
				//Added in to set the text on GUI to the current player
				if(isGUI() == true)
					GUI.sayMsg("Players Turn"); 
			}else if(playerTurn == 1){
				System.out.println("AI 1's Turn");
				if(isGUI() == true)
					GUI.sayMsg("AI 1's Turn");
			}else if(playerTurn == 2){
				System.out.println("AI 2's Turn");
				if(isGUI() == true)
					GUI.sayMsg("AI 2's Turn");
			}else if(playerTurn == 3){
				if(isGUI() == true)
					GUI.sayMsg("AI 3's Turn");
				System.out.println("AI 3's Turn");
				playerTurn = playerTurn%3;
				break;
			}
			
			if(isGUI() == true) //Make sure gui is set then update tiles
				gui.updateTiles();
			
			playerTurn++;
		}
	}

	public void drawTile() {
		Tile drawTile = initDeck.remove(0);
		if(playerTurn == 0) {
			if(isGUI() == true)
				GUI.sayMsg("Player draws tile"); 
			player0.addTile(drawTile);
			System.out.println("Players draws a tile");
		}else if(playerTurn == 1){
			System.out.println("AI 1's draws a tile");
			if(isGUI() == true)	
				GUI.sayMsg("AI 1's draws a tile"); 
			player1.addTile(drawTile);
		}else if(playerTurn == 2){
			System.out.println("AI 2's draws a tile");
			if(isGUI() == true)
				GUI.sayMsg("AI 2's draws a tile"); 
			player2.addTile(drawTile);
		}else if(playerTurn == 3){
			System.out.println("AI 3's draws a tile");
			if(isGUI() == true)
				GUI.sayMsg("AI 3's draws a tile"); 
			player3.addTile(drawTile);
		}
		if(isGUI() == true) //Make sure gui is set then update tiles
			gui.updateTiles();
	}

	public boolean checkPlays(ArrayList<ArrayList<Tile>> temp1) {
		if(player0.checkPlays(temp1)) {
			for(int i = 0; i < temp1.size(); i++) {
				addMend(temp1.get(i));
			}
		}
		return false;
	}
	
	public void resetStaticVars() {
		player0 = new Player();
		player1 = new AI(new Strategy1());
		player2 = new AI(new Strategy2());
		player3 = new AI(new Strategy3());
		field = new ArrayList<ArrayList<Tile>>();
		fieldSize = 0;
	}
	
	public static int[] getHandSizeOfOtherPlayers(Player asker) {
		int[] holder = new int[3]; //assuming always 3 other players
		if(asker == player3) { //for now only used by p3
			holder[0] = player0.getHand().size();
			holder[1] = player1.getHand().size();
			holder[2] = player2.getHand().size();
		}
		return holder;
	}

	public void checkPerimeter(double xCord, double yCord, int tileInfo,Tile tile) {
		if(player0.tilesOnField.size() == 0) {
			System.out.println("1");
			player0.addToDummyField(tile, 0, field); // only 0 when no other tiles on the field
		}else {
			System.out.println("3");
			player0.addToDummyField(tile, 1, field);
		}
	}
}
