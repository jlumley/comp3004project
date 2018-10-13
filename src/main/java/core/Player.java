package core;

import java.util.ArrayList;

public class Player {
	private ArrayList<Tile> hand = new ArrayList<Tile>();
	
	private void Player() {
		hand = new ArrayList<Tile>();
	}
	
	public ArrayList<Tile> getHand() {
		return hand;
	}
	
	public void addTile(Tile t) {
		hand.add(t);
	}
	
	public void drawTile(ArrayList<Tile> collection) {
		if(!collection.isEmpty())
			addTile(collection.remove(0));
	}
	
	public int countTiles() {
		return this.hand.size();
	}
}

