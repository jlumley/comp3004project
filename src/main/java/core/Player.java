package core;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private ArrayList<Tile> hand = new ArrayList<Tile>();
	public boolean firstTurn = false; // need group thoughts
	ArrayList<ArrayList<Tile>> tilesOnField = new ArrayList<ArrayList<Tile>>(); // used to store tiles used on turn 
	public ArrayList<ArrayList<Tile>> getTilesUsed(){
		return tilesOnField;
	}
	
	private void Player() {
		hand = new ArrayList<Tile>();
	}
	
	public ArrayList<Tile> getHand() {
		return hand;
	}
	public void showHand() { 
		System.out.println(hand);
	}
	public void addTile(Tile t) {
		hand.add(t);
		Collections.sort(hand);
	}
	
	public void drawTile(ArrayList<Tile> collection) {
		if(!collection.isEmpty())
			addTile(collection.remove(0));
	}
	
	public int countTiles() {
		return this.hand.size();
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
				n+=1;
				if (n >= this.hand.size()) {
					break;
				}
			}
			// if you are able to make a meld of at least 3 tiles
			// then make a new hand without the tiles used in the meld
			if (tmp.size() >= 3) {
				melds.add(tmp);
				ArrayList<Tile> newHand = new ArrayList<Tile>();
				for (Tile t: this.hand) {
					if (!tilesToRemove.contains(t.getId())) {
						newHand.add(t);
					}
				}
				this.hand = newHand;
			}
		}
		return melds;
	}
	
	public ArrayList<ArrayList<Tile>> findRuns() {
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
			while (n < this.hand.size()) {
				if (this.hand.get(n).getColour() == colour &&
					this.hand.get(n).getValue() == value+1) {
					tmp.add(this.hand.get(n));
					tilesToRemove.add(this.hand.get(n).getId());
					value += 1;
				}
				n+=1;
			} 
			// if you are able to make a meld of at least 3 tiles
			// then make a new hand without the tiles used in the meld
			if (tmp.size() >= 3) {
				melds.add(tmp);
				ArrayList<Tile> newHand = new ArrayList<Tile>();
				for (Tile t: this.hand) {
					if (!tilesToRemove.contains(t.getId())) {
						newHand.add(t);
					}
				}
				this.hand = newHand;
			}
		}
		return melds;
	}

	
	public ArrayList<ArrayList<Tile>> inHandMelds(){
		ArrayList<ArrayList<Tile>> melds = new ArrayList<ArrayList<Tile>>();
		ArrayList<ArrayList<Tile>> sets = this.findSets();
		ArrayList<ArrayList<Tile>> runs = this.findRuns();
		for (ArrayList<Tile> set: sets) {
			melds.add(set);
		}
		for (ArrayList<Tile> set: runs) {
			melds.add(set);
		}
		return melds;
	}
	public void addMendtoMain(ArrayList<Tile> collection) { // basic adding into the field of tiles
		TileRummyMain.addMend(collection);
	}
	/*
	public boolean checkSet(ArrayList<ArrayList<Tile>> collection){ // checks for users first hand with sets 
		// move this to player after team meeting
		boolean returnV = true;
		int checkSum = 0;
		ArrayList<String> suitDeck = new ArrayList<String>();
		int checkValue = collection.get(0).get(0).getValue();
		if(collection.size() == 0) {
			return false;
		}else {
			for(int i = 0; i < collection.size(); i++) {
				for(int x = 0; x < collection.get(i).size(); x++) {
					if(!suitDeck.contains(collection.get(i).get(x).getColour())) {
						if(collection.get(i).get(x).getValue() == checkValue){
							suitDeck.add(collection.get(i).get(x).getColour());
							checkSum += collection.get(i).get(x).getValue();
						}
					}
				}
			}
		}
		System.out.println(checkSum + " " + suitDeck + " " + checkValue);
		if(checkSum < 30) {
			returnV = false;
		}
		if(returnV) {
			for(int i = 0; i < collection.size(); i++) {
				System.out.println(checkSum);
				addMendtoMain(collection.get(i));
			}
		}
		return returnV;
	}*/

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
			System.out.println(checkSum);
		}
		
		System.out.println(checkSum + " " + suitDeck + " " + checkValue + " " + returnV + " Sets");
		return returnV;
	}
	
	public boolean checkPlays(ArrayList<ArrayList<Tile>> temp1) { // goes through each 'play' and splits them off into different reuseable functions
		for(int i = 0; i < temp1.size(); i++) {
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
				System.out.println(tile.getColour() + " " + colourString);
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
}

