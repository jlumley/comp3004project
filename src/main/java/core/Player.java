package core;

import java.util.ArrayList;

public class Player {
	private ArrayList<Tile> hand;
	
	private void Player() {
		this.hand  = new ArrayList<Tile>();
	 	
	}
	
	public ArrayList<Tile> getHand() {
		return this.hand;
	}
	
	public void addTile(Tile t) {
		this.hand.add(t);
	}
	
	public void drawTile(ArrayList<Tile> collection) {
		this.addTile(collection.remove(0));
	}
}

