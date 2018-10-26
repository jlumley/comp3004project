package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Player {
	private ArrayList<Tile> hand = new ArrayList<Tile>();
	private int jokers = 0;
	
	private void Player() {
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
	}
	
	public void drawTile(ArrayList<Tile> collection) {
		if(!collection.isEmpty())
			addTile(collection.remove(0));
	}
	
	public int countTiles() {
		return this.hand.size();
	}
	
	public ArrayList<ArrayList<Tile>> playAllTiles(ArrayList<ArrayList<Tile>> tableTiles) {
		ArrayList<ArrayList<Tile>> newTable = new ArrayList<ArrayList<Tile>>();
		Set<Tile> playableTiles = new HashSet<Tile>(this.getHand());
		for (ArrayList<Tile> meld: tableTiles) {
			playableTiles.addAll(meld);
		}
		
		int [][] tilesMatrix = tileSetToMatrix(playableTiles);
		Hand hand = new Hand(tilesMatrix, this.jokers);
		
		this.findMelds(hand, 0, 0);
		
		return hand.getMelds();
	}
	
	public static int [][] tileSetToMatrix(Set<Tile> tileSet){
		int[][] matrix = new int[4][13];
		
		for (Tile t: tileSet) {
			if(t.joker) continue;
			int colour = 0;
			if (t.getColour() == "R") colour=0;
			if (t.getColour() == "B") colour=1;
			if (t.getColour() == "G") colour=2;
			if (t.getColour() == "Y") colour=3;
			
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
		int jokers = hand.jokers;
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
	            case 3:  suitString = "Y";
	                     break;
				}
				run.add(new Tile(suitString, value+1));
			} else if(jokers > 0) {
				run.add(new Tile("X", 99));
			} else break;
			value ++;
		}
		
		while (run.size() < 3 && jokers > 0) {
			run.add(new Tile("X", 99));
			jokers--;
		}
		
		while (run.size() > 3 && run.get(run.size()-1).joker) {
			run.remove(run.size()-1);
			jokers++;
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
		            case 3:  suitString = "Y";
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

			while (jokers > 1 && candidatePairs.size() > 0) {
				int select = candidateTiles.get(candidateTiles.size()-1).get(0).getValue();
				for (int i=candidatePairs.size()-1; i>=0 && i<candidatePairs.size()-3; i--) {
					select -= candidatePairs.get(i).get(0).getValue()*2;
				}
				if (select > 0) {
					set = candidateTiles.remove(candidateTiles.size()-1);
					set.add(new Tile("X", 99));
					set.add(new Tile("X", 99));
					hand.addToMelds(set);
				} else {
					for (int i=0; i<2 && candidatePairs.size() > 0; i++) {
						set = candidatePairs.remove(candidatePairs.size()-1);
						set.add(new Tile("X", 99));
						hand.addToMelds(set);
					}
				}
			}

			while (jokers > 0 && candidatePairs.size()>0) {
				set = candidatePairs.remove(candidatePairs.size()-1);
				set.add(new Tile("X", 99));
				hand.addToMelds(set);
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
					Hand testHand = new Hand(hand.hardCopyMatrix(), jokers);
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
			}
		}
		return hand;
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
			else if (t.getColour() == "Y") {
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

