package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;


public class Player {
	private int jokers = 0;
	public ArrayList<Tile> oldHand = new ArrayList<Tile>();
	protected ArrayList<Tile> hand = new ArrayList<Tile>();
	public boolean firstTurn = false; // need group thoughts
	ArrayList<ArrayList<Tile>> tilesOnField = new ArrayList<ArrayList<Tile>>(); // used to store tiles used on turn 
	public ArrayList<ArrayList<Tile>> getTilesUsed(){
		return tilesOnField;
	}

	
	public void Player() {
		hand = new ArrayList<Tile>();
		jokers = this.getJokers();
		
	}
	
	public int getJokers() {
		int jokers = 0;
		for (Tile t: hand) {
			if (t.joker) {
				jokers += 1;
			}
		}
		return jokers;
	}
	
	public ArrayList<Tile> getHand() {
		return hand;
	}
	public void showHand() { 
		System.out.println(hand);
	}
	public void addTile(Tile t) {
		hand.add(t);
		this.jokers = getJokers();
		Collections.sort(hand);
		
		oldHand = hand;
	}
	
	public void drawTile(ArrayList<Tile> collection) {
		if(!collection.isEmpty())
			addTile(collection.remove(0));
		oldHand = hand;
	}
	
	public int countTiles() {
		return this.hand.size();
	}

	
	public ArrayList<ArrayList<Tile>> playAllTiles(ArrayList<ArrayList<Tile>> tableTiles) {
		// returns the new table
		ArrayList<ArrayList<Tile>> newTable = new ArrayList<ArrayList<Tile>>();
		Set<Tile> playableTiles = new HashSet<Tile>(this.getHand());
		for (ArrayList<Tile> meld: tableTiles) {
			playableTiles.addAll(meld);
		}
		
		int [][] tilesMatrix = tileSetToMatrix(playableTiles);
		Hand hand = new Hand(tilesMatrix, this.jokers);
		
		this.findMelds(hand, 0, 0);
		this.hand = Player.matrixToTileArray(hand);
		//System.out.println(this.hand);
		
		return hand.getMelds();
	}
	
	
	public ArrayList<ArrayList<Tile>> playAllTiles() {
		//returns an arraylist of melds to add to the table
		ArrayList<ArrayList<Tile>> newTable = new ArrayList<ArrayList<Tile>>();
		Set<Tile> playableTiles = new HashSet<Tile>(this.getHand());

		int [][] tilesMatrix = tileSetToMatrix(playableTiles);
		Hand hand = new Hand(tilesMatrix, this.jokers);
		
		this.findMelds(hand, 0, 0);
		this.hand = Player.matrixToTileArray(hand);
		System.out.println("Hand: " + this.hand);
		
		return hand.getMelds();
	}
	
	public static ArrayList<Tile> matrixToTileArray(Hand hand) {
		ArrayList<Tile> newHand = new ArrayList<Tile>();
		for (int suit = 0; suit < 4; suit++) {
			for (int value=0; value < 13; value++) {
				while (hand.cards[suit][value] > 0) {
					String suitString = new String();
					switch (suit) {
		            case 0:  suitString = "R";
		                     break;
		            case 1:  suitString = "B";
		                     break;
		            case 2:  suitString = "G";
		                     break;
		            case 3:  suitString = "O";
		                     break;
					}
					hand.cards[suit][value]--;
					newHand.add(new Tile(suitString, value+1));
				}
			}
		}
		return newHand;
	}


	public ArrayList<ArrayList<Tile>> findSets() {
		ArrayList<ArrayList<Tile>> melds = new ArrayList<ArrayList<Tile>>();
		for (int i=0; i < this.hand.size()-1; i++) {
			int value = this.hand.get(i).getValue();
			String colour = this.hand.get(i).getColour();
			int n = i+1;
			ArrayList<Tile> tmp = new ArrayList<Tile>();
			Set<Integer> tilesToRemove = new HashSet<Integer>();
			tmp.add(this.hand.get(i));
			tilesToRemove.add(i);
			// check to see if the following cards are of the same value
			// but that they have different colours
			while (this.hand.get(n).getValue() == value) {
				if (this.hand.get(n).getColour() != colour) {
					tmp.add(this.hand.get(n));
					tilesToRemove.add(this.hand.get(n).getId());

				}
			}
		}
		return melds;
	}
	
	public static int [][] tileSetToMatrix(Set<Tile> tileSet){
		int[][] matrix = new int[4][13];
		
		for (Tile t: tileSet) {
			if(t.joker) continue;
			int colour = 0;
			if (t.getColour() == "R") colour=0;
			if (t.getColour() == "B") colour=1;
			if (t.getColour() == "G") colour=2;
			if (t.getColour() == "O") colour=3;
			
			matrix[colour][t.getValue()-1] = matrix[colour][t.getValue()-1] + 1;
		}
		
		return matrix;
	}
	
	public static void printMatrix(int [][] m) {
		for (int n=0; n<4; n++) {
			for (int i=0; i<13; i++) {
				System.out.print(m[n][i]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public ArrayList<Tile> findRun(Hand hand, int suit, int value) {
		ArrayList<Tile> run = new ArrayList<Tile>();
		while (value < 13) {
			if (hand.cards[suit][value] > 0) {
				String suitString = new String();
				switch (suit) {
	            case 0:  suitString = "R";
	                     break;
	            case 1:  suitString = "B";
	                     break;
	            case 2:  suitString = "G";
	                     break;
	            case 3:  suitString = "O";
	                     break;
				}
				run.add(new Tile(suitString, value+1));
			} else if(hand.jokers > 0) {
				run.add(new Tile("X", 99));
				hand.jokers--;
			} else break;
			value ++;
		}
		
		while (run.size() < 3 && hand.jokers > 0) {
			run.add(new Tile("X", 99));
			hand.jokers--;
		}
		
		while (run.size() > 3 && run.get(run.size()-1).joker) {
			run.remove(run.size()-1);
			hand.jokers++;
		}
		return run;
	}
	
	public Hand findSets(Hand hand) {
		ArrayList<ArrayList<Tile>> sets = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> candidateTiles = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> candidatePairs = new ArrayList<ArrayList<Tile>>();
		for (int rank = 0; rank < 13; rank++) {
			ArrayList<Tile> set = new ArrayList<Tile>();
			int value = 0;

			for (int suit = 0; suit < 4; suit++) {
				if (hand.cards[suit][rank] > 0) {
					String suitString = new String();
					switch (suit) {
		            case 0:  suitString = "R";
		                     break;
		            case 1:  suitString = "B";
		                     break;
		            case 2:  suitString = "G";
		                     break;
		            case 3:  suitString = "O";
		                     break;
					}
					set.add(new Tile(suitString, rank+1));
					value += rank+1;
				}

			}
			if (set.size() >= 3) {
				hand.addToMelds(set);
			} else if (set.size() == 1) {
				candidateTiles.add(set);
			} else if (set.size() == 2) {
				candidatePairs.add(set);
			}

			while (hand.jokers >= 1 && candidatePairs.size() > 0) {
				int select = candidateTiles.get(candidateTiles.size()-1).get(0).getValue();
				for (int i=candidatePairs.size()-1; i>=0 && i<candidatePairs.size()-3; i--) {
					select -= candidatePairs.get(i).get(0).getValue()*2;
				}
				if (select > 0) {
					set = candidateTiles.remove(candidateTiles.size()-1);
					set.add(new Tile("X", 99));
					set.add(new Tile("X", 99));
					hand.jokers -=2;
					hand.addToMelds(set);
				} else {
					for (int i=0; i<2 && candidatePairs.size() > 0; i++) {
						set = candidatePairs.remove(candidatePairs.size()-1);
						set.add(new Tile("X", 99));
						hand.jokers --;
						hand.addToMelds(set);
					}
				}
			}

			while (jokers > 0 && candidatePairs.size()>0) {
				set = candidatePairs.remove(candidatePairs.size()-1);
				set.add(new Tile("X", 99));
				hand.addToMelds(set);
				hand.jokers--;
			}

		}
		return hand;
	}
	
	public Hand findMelds(Hand hand, int suit, int value) {
		while (suit < 4 && hand.getValue() > 0) {
			if (hand.cards[suit][value] > 0) {
				ArrayList<Tile> run = this.findRun(hand, suit, value); 
				for (int len=3; len <= run.size(); len++) {
					if (len>3 && run.get(len-1).joker) continue;
					Hand testHand = new Hand(hand.hardCopyMatrix(), hand.jokers);
					testHand.removeCards(new ArrayList<Tile>(run.subList(0, len)));
					testHand = this.findMelds(testHand, suit, value);

					if (testHand.getValue() < hand.getValue()) {
						//hand.melds = new ArrayList<ArrayList<Tile>>();
						testHand.melds.add(new ArrayList<Tile>(run.subList(0, len)));
						hand.melds.addAll(testHand.melds);
						hand.cards = testHand.cards;
						hand.jokers = testHand.jokers;  
					}
				}
			}
			value ++;
			if (value > 12) {
				value = 0;
				suit++;
			}
		}
	
		if (hand.getValue() > 0) {
			Hand testHand = new Hand(hand.cards, hand.jokers);
			testHand = this.findSets(testHand);
			
			if (testHand.getValue() <= hand.getValue()) { 
				hand.cards = testHand.cards;
				hand.melds.addAll(testHand.melds);
				hand.jokers = testHand.jokers;
			}
		}
		return hand;
	}		
	public void addMendtoMain(ArrayList<Tile> collection) { // basic adding into the s of tiles
		TileRummyMain.addMend(collection);
	}

	public boolean checkSet(ArrayList<Tile> collection){ // checks for users first hand with sets 
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
				}else {
					returnV = false;
				}
			}
		}
		if(returnV) {
			//System.out.println("Total played: " + checkSum);
		}
		System.out.println("Total: " + checkSum + " " + suitDeck + " " + checkValue + " " + returnV + " Sets");
		return returnV;
	}
	
	public boolean checkPlays(ArrayList<ArrayList<Tile>> temp1) { // goesf through each 'play' and splits them off into different reuseable functions

		for(int i = 0; i < temp1.size(); i++) {
			if(temp1.get(i).size() < 3) {
				return false;
			}
				if(temp1.get(i).get(0).getValue() == temp1.get(i).get(1).getValue()) { // if it is a set
					if(!checkSet(temp1.get(i))) {
						return false;
					}
				}else {
					if(!checkRun(temp1.get(i))) {
						return false;
					}
				}
		}
		if(firstTurn == false) {
			if(getTotal(temp1) >= 30) {
				firstTurn = true;
			}else {
				return false;
			}
		}
		return true;
	}

	private int getTotal(ArrayList<ArrayList<Tile>> temp1) {
		int total = 0;
		for(int i = 0; i < temp1.size(); i++) {
			for(int x = 0; x < temp1.get(i).size(); x++) {
				total += temp1.get(i).get(x).getValue();
			}
		}
		return total;
	}

	private boolean checkRun(ArrayList<Tile> temp1) { // still have to check for 30points and so on later.
		boolean returnB = true;
		String colourString = temp1.get(0).getColour();
		int counter = 0;
		ArrayList<Integer> valuesOfRun = new ArrayList<Integer>(); 
		for(Tile tile : temp1) { // checks for the same colour
			valuesOfRun.add(tile.getValue()); // adds it into the array
			if(!tile.getColour().equals(colourString)) { // if not same colour
				System.out.println("Tile: " + tile.getColour() + " " + colourString);
				return false;
			}
		}
		counter = valuesOfRun.size();

		int minInt = Collections.min(valuesOfRun); // finds the lowest in the run
		valuesOfRun.remove((Integer) minInt); // remove from dummy list
		
		for(int x = 0; x < counter; x++) {
			if(valuesOfRun.contains(minInt+1)) { // goes through it and checks for min + 1 
				minInt += 1; // new pivot
				valuesOfRun.remove((Integer) minInt); // remove the newest pivot
			}
		}
		if(valuesOfRun.isEmpty()) {
			returnB = true;
		}else {
			returnB = false;
		}
		return returnB;
  }
	
	public boolean removeTileFromHand(Tile tile) {
		for (Tile t: this.hand) {
			if (tile.getId() == t.getId()) {
				this.hand.remove(t);
				return true;
			}
		}
		return false;
	}

	public void addToDummyField(Tile tile, int arrayPos, ArrayList<ArrayList<Tile>> currField) {
		System.out.println("Current Field" + currField);
		checkInDummyField(tile);
		boolean onDummy = false;
		boolean onField = false;
		if(arrayPos == 0) { // adds if nothing on the field
			ArrayList<Tile> dummyHand = new ArrayList<Tile>();
			dummyHand.add(tile);
			tilesOnField.add(dummyHand);

			System.out.println("Dummy tiles on FIeld " + tilesOnField);

			}else {
				double xTile = tile.getX();
				double yTile = tile.getY();
				
				for(int i = 0; i < tilesOnField.size(); i++) { // check everything on the dummyField.
					for(int x = 0; x < tilesOnField.get(i).size(); x++) { // current position
						double xTile2 = tilesOnField.get(i).get(x).getX();
						double xFinal = Math.abs(xTile - xTile2);
            
						
						if(xFinal >= 0 && xFinal <= 33) {
							double yTile2 = tilesOnField.get(i).get(x).getY();
							double yFinal = Math.abs(yTile - yTile2);
							
							if(yFinal >= 0 && yFinal <= 33) {
								tilesOnField.get(i).add(tile);
								onDummy = true;
								break;

							}
						}
					}
				}
				if(!onDummy) {
					for(int i = 0; i < currField.size(); i++) { // check everything on the dummyField.
						for(int x = 0; x < currField.get(i).size(); x++) { // current position
							double xTile2 = currField.get(i).get(x).getX();
							System.out.println("a " + currField.get(i).get(x).getColour() + " " + currField.get(i).get(x).getValue());
							System.out.println("b " + currField.get(i).get(x).getX() + " " + currField.get(i).get(x).getY());
							double xFinal = Math.abs(xTile - xTile2);
							if(xFinal >= 0 && xFinal <= 33) {
								double yTile2 = tilesOnField.get(i).get(x).getY();
								double yFinal = Math.abs(yTile - yTile2);
								if(yFinal >= 0 && yFinal <= 33) {
									tilesOnField.get(i).add(tile);
									onField = true;

								}
							}
						}
					}
				}
				if(!onField && !onDummy) {
					ArrayList<Tile> dummyHand = new ArrayList<Tile>();
					dummyHand.add(tile);
					tilesOnField.add(dummyHand);
				}
			}
			
		System.out.println("Tiles afterwards on Field: " + tilesOnField);
		}


	private void checkInDummyField(Tile tile) {
		for(int i = 0; i < tilesOnField.size(); i++) { // checks if in dummy array
			for(int x = 0; x < tilesOnField.get(i).size(); x++) {
				if( tilesOnField.get(i).get(x).getId() == tile.getId() ) {
					tilesOnField.get(i).remove(x);
				}
			}
		}
		for(int a = 0; a < tilesOnField.size(); a++) { // deletes empty arrays
			if(tilesOnField.get(a).size() == 0) {
				tilesOnField.remove(a);
			}
		}
	}


	public void clearDummyHand() {
		tilesOnField.clear();	
	}



}
class Hand {
	public int [][]cards = new int[4][13];
	public int jokers = 0;
	public ArrayList<ArrayList<Tile>> melds = new ArrayList<ArrayList<Tile>>();
	
	public Hand(int[][] cards, int jokers) {
		this.cards = cards;
		this.jokers = jokers;
	}
			
	public int getValue() {
		int value = 0;
			
		for (int i = 0; i<4; i++) {
			for(int n = 0; n<13; n++) {
				if (this.cards[i][n] != 0) {
					value += (n+1 * (this.cards[i][n]));
				}
			}
		}
		return value;
	}
	
	public void addToMelds(ArrayList<Tile> tiles) {
		this.melds.add(tiles);
		this.removeCards(tiles);
	}
	
	public void removeCards(ArrayList<Tile> tiles) {
		
		for (Tile t: tiles) {
			if (t.joker) {
				this.jokers--;
				continue;
			}
			int suit = 0;
			if (t.getColour() == "R") {
			suit = 0;
		}
		else if (t.getColour() == "B") {
			suit = 1;			
		}
		else if (t.getColour() == "G") {
			suit = 2;
		}
		else if (t.getColour() == "O") {
				suit = 3;
			}
			if (this.cards[suit][t.getValue()-1] > 0) {
				this.cards[suit][t.getValue()-1] -= 1;
			}
			
		}		
	}
	
	public int[][] hardCopyMatrix() {
		
		int[][] copy = new int[4][13];
		for (int n=0; n<4; n++) {
			for (int i=0; i<13; i++) {
				copy[n][i] = this.cards[n][i]; 
			}
		}
		return copy;
	}
	
	public ArrayList<ArrayList<Tile>> getMelds() {
		pruneHand();
		return this.melds;
	}
	
	public void pruneHand() {
		for (int i=0; i<this.melds.size()-1; i++) {
			if (this.melds.get(i+1).size() - this.melds.get(i).size() == 1) {
				if (this.melds.get(i+1).get(0) == this.melds.get(i).get(0)) {
					if (this.melds.get(i+1).get(1) == this.melds.get(i).get(1)) {
						if (this.melds.get(i+1).get(2) == this.melds.get(i).get(2)) {
							this.melds.remove(i);
						}
					}
				} 
			}
		}
	}
		
	
}

