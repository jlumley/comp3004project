package core;

import java.io.*;
import java.util.*;


public class TileRummyMain{
	
	public static final String[] suites = {"R", "B", "G", "O"};
	public static final int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	public static ArrayList<ArrayList<Tile>> field = new ArrayList<ArrayList<Tile>>();
	public static ArrayList<ArrayList<Tile>> rollbackField = new ArrayList<ArrayList<Tile>>();
	public static ArrayList<ArrayList<Tile>> justPlayed = new ArrayList<ArrayList<Tile>>();
	static ArrayList<Tile> initDeck = new ArrayList<Tile>();
	List<String> initDeckDummy = new ArrayList<String>();
	public static Player player0 = new Player();
	public static AI player1;
	public static AI player2;
	public static AI player3;
	boolean gameStatus = true;
	public int playerTurn = 0;
	public boolean firstTurnTracker = false;
	
	public ArrayList<ArrayList<Tile>> getField(){return field;}
	
	/* Provide option to select which strategies to use*/
	/* Options are Player, AI Strategy 1, AI Strategy 2, AI Strategy 3, AI Strategy 4*/
	public void initialize(String filename, ArrayList<String> options) {
		player0 = new Player();
		player1 = setStrategy(options.get(1));
		player2 = setStrategy(options.get(2));
		player3 = setStrategy(options.get(3));
				
		resetStaticVars();
		initDeck = buildDeck(suites,values);
		Collections.shuffle(initDeck);
		dealHands(filename);
	}
	
	public AI setStrategy(String choice)
	{
		if(choice == null)
		{
			return null;
		}
		
		if(choice.equals("AI Strategy 1"))
		{
			return new AI(new Strategy1());
		}
		else if(choice.equals("AI Strategy 2"))
		{
			return new AI(new Strategy2());
		}
		else if(choice.equals("AI Strategy 3"))
		{
			return new AI(new Strategy3());
		}
		else
		{
			//Assume this is a player
			return null;
		}
	}
	public void initialize(String filename) {
		player0 = new Player();
		player1 = new AI(new Strategy1());
		player2 = new AI(new Strategy2());
		player3 = new AI(new Strategy3());
		resetStaticVars();
		initDeck = buildDeck(suites,values);
		Collections.shuffle(initDeck);
		dealHands(filename);
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
		for(int i = 0; i < initDeck.size(); i++) {
			System.out.print( initDeck.get(i).toString() + " ");
		}
	}
	
	public void dealHands(String filename) {
		
		if (!filename.isEmpty()) {
			System.out.println("using file to deal hands");
			dealHandsFromFile(filename);
		}else {
			for(int i = 0; i < 14; i++) {
				player0.drawTile(initDeck);
				player1.drawTile(initDeck);
				player2.drawTile(initDeck);
				player3.drawTile(initDeck);
			}
		}
	}

	public void showHands() {
		System.out.println("User's 1 Hand: " + player1.getHand() );
		
	}
	public void getHandSize() { 
		
	}
	public void showField() {
		System.out.println(field);
		System.out.print("Field : ");
			for(int x = 0; x < field.size(); x++) { // gets the length of the first object in the first position
				if(justPlayed.contains(field.get(x))) {
					System.out.print("*");
				}
				System.out.print(field.get(x) + ",");
			}
			System.out.println(); //spacing
		System.out.println(justPlayed);
	}
	
	public static void addMend(ArrayList<Tile> collection1) { // basic adding into the field of tiles
		System.out.println("Size: " + collection1.size());
		field.add(collection1);
		System.out.println("Add to Field " + field.size());
	}

	public boolean checkGameStatus() {
		if(player0.getHand().size() == 0) {
			gameStatus = false;
			System.out.println("Player has won!");
		}else if(player1.getHand().size() == 0) {
			gameStatus = false;
			System.out.println("User 2 has won!");
		}else if(player2.getHand().size() == 0) {
			gameStatus = false;
			System.out.println("User 3 has won!");
		}else if(player3.getHand().size() == 0) {
			gameStatus = false;
			System.out.println("User 4 has won!");
		}
		return gameStatus;
	}

	public void playGame() { // where most of the game logic is going to go.
		while(gameStatus){
			if(playerTurn == 0) {
				System.out.println("Players Turn");
				//Added in to set the text on GUI to the current player
				//GUI.setPlayerTurn("player1"); 
			}else if(playerTurn == 1 ){
				System.out.println("Just played: " + justPlayed);
				
				if(field.size() == rollbackField.size()) { // when nothing is changed
					player0.drawTile(initDeck);
				}
				System.out.println("AI 1's Turn");
				if(firstTurnTracker) {
					player1.playTurn();
				}
				//GUI.setPlayerTurn("player2");
			}else if(playerTurn == 2){
				System.out.println("AI 2's Turn");
				if(firstTurnTracker) {
					
				}
				//player2.playTurn();
				//GUI.setPlayerTurn("player3");
			}else if(playerTurn == 3){
				//GUI.setPlayerTurn("player4");
				System.out.println("AI 3's Turn");
				//player3.playTurn();
				if(firstTurnTracker) {
					
				}
				firstTurnTracker = true;
				playerTurn = playerTurn%3;
				if(checkField()) {
					cloneField();
				}
				break;
			}
			System.out.println(field);
			System.out.println("------------------------------");
			playerTurn++;
		}
	}


	public boolean checkPlays(ArrayList<ArrayList<Tile>> temp1) {
		int tmpsize1 = temp1.size();
		System.out.println(player0.checkPlays(temp1));
		if(player0.checkPlays(temp1)) {
			for(int i = 0; i < tmpsize1-1; i++){
				System.out.println(field.contains(temp1.get(i)));
				if(!field.contains(temp1.get(i))) {
					addMend(temp1.get(i));
					justPlayed.add(temp1.get(i));
				}
				System.out.println("Player played " + temp1.get(i));
				System.out.println("for: " + tmpsize1);
			}
		}
		System.out.println(temp1);
		System.out.println("Player's New Hand: " + player0.getHand() + " " + tmpsize1);
		
		return false;
	}
	
	public void resetStaticVars() {
		player0 = new Player();
		player1 = new AI(new Strategy1());
		player2 = new AI(new Strategy2());
		player3 = new AI(new Strategy3());
		field = new ArrayList<ArrayList<Tile>>();
		initDeck = new ArrayList<Tile>();
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
	
	public static boolean isValidTable(ArrayList<ArrayList<Tile>> table) {
		for (ArrayList<Tile> meld: table) {
			if (!validRun(meld) && !validSet(meld)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validSet(ArrayList<Tile> set) {
		if (set.size() > 4) return false;
		Set<String> colours = new HashSet<>();
		int value = set.get(0).getValue();
		for (Tile t: set) {
			if (t.getValue() != value) {
				return false;
			}
			if (colours.contains(t.getColour())) {
				return false;
			} else colours.add(t.getColour());	
		}
		return true;
	}
	
	public static boolean validRun(ArrayList<Tile> run) {
		String colour = run.get(0).getColour();
		int value = 0;
		for (Tile t: run) {
			if (value == 0) {
				value = t.getValue();
				continue;
			}
			if (!t.getColour().equals(colour)) {
				return false;
			}
			if (t.getValue() != value+1) {
				return false;
			}else value++;
			
		}
		return true;
	}
	public int getTurn() {
		return playerTurn;
	}
	
	public boolean checkField() {
		if(player0.checkPlays(field)) { // if true 
			return true;
		}else {
			return false;
		}
	}

	public void cloneField() {
		rollbackField.clear();
		System.out.println(rollbackField);
		for(int i = 0; i < field.size(); i++){
			rollbackField.add(new ArrayList<Tile>(field.get(i)));
		}
		System.out.println(rollbackField + " is this now");
	}
	
	public void rollbackNow() {
		field.clear();
		for(int i = 0; i < field.size(); i++){
			field.add(new ArrayList<Tile>(rollbackField.get(i)));
		}
		System.out.println("field before reroll" + field);
	}
	
	public void dealHandsFromFile(String filename) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       if (line.matches("P0:(.*)")){
		    	   System.out.println(line);
		    	   line = line.replaceAll("P0: ", "");
		    	   String []cards = line.split(",");
		    	   for (String s : cards) {
		    		   player0.addTile(new Tile(s.trim()));
		    	   }
			   }
		       if (line.matches("P1:(.*)")){
		    	   System.out.println(line);
		    	   line = line.replaceAll("P1: ", "");
		    	   String []cards = line.split(",");
		    	   for (String s : cards) {
		    		   player1.addTile(new Tile(s.trim()));
		    	   }
			   }
		       if (line.matches("P2:(.*)")){
		    	   System.out.println(line);
		    	   line = line.replaceAll("P2: ", "");
		    	   String []cards = line.split(",");
		    	   for (String s : cards) {
		    		   player2.addTile(new Tile(s.trim()));
		    	   }
			   }
		       if (line.matches("P3:(.*)")){
		    	   System.out.println(line);
		    	   line = line.replaceAll("P3: ", "");
		    	   String []cards = line.split(",");
		    	   for (String s : cards) {
		    		   player3.addTile(new Tile(s.trim()));
		    	   }
			   }

		       
		    }
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
