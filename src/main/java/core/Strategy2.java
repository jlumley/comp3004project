package core;

import java.util.ArrayList;

public class Strategy2 implements AIStrategy{
	private AI player;
	
	public void playTurn() {
		//Check board for existing tiles; 
		// if Exists Check for 30 point limit and play
		// else wait
		
		//Play:
			//if: can Win then play all tiles and win
			//else: keep all in-hand melds, and try adding to existing melds on board
		System.out.println("Player using Strat2 Turn");
		System.out.println("Player using Strat2 Hand: " + player.getHand());
		ArrayList<ArrayList<Tile>> p2Melds = player.playAllTiles();
		System.out.println("Possible Melds in Hand: " + p2Melds);
		if(player.firstTurn == false) { //first turn has not passed yet
			if(TileRummyMain.field.size() <= 0) { //if no-one else has played
				returnCardsAndDrawCard(p2Melds);
			}
			else {
				if(player.checkPlays(p2Melds)) { //checks for >=30 size
					for(int i = 0; i < p2Melds.size(); i++) { //Play all the tiles it can
						TileRummyMain.addMend(p2Melds.get(i)); //add all melds to board
					}
					player.firstTurn = true;
				}
				else { //return cards to hand and draw a card
					returnCardsAndDrawCard(p2Melds);
				}
			}
		}
		else { // not first turn
			ArrayList<ArrayList<Tile>> p2NewBoard = player.playAllTiles(TileRummyMain.field);
			//Check if can win this turn, if so Win
			if(player.countTiles() == 0) {
				TileRummyMain.field = p2NewBoard; //replace old board with this new one
				for(int i = 0; i < p2Melds.size(); i++) { 
					TileRummyMain.addMend(p2Melds.get(i)); 
				}
				return;
			}
			//not insta-Win then, keep all in-hand melds, and try adding to existing melds on board
			if(TileRummyMain.field.size() != p2NewBoard.size()) { //check num of melds
				TileRummyMain.field = p2NewBoard; //replace old board with this new one
				return;
			}
			else { //if equal num melds
				for(int i = 0; i < TileRummyMain.field.size(); i++) { //check size of each meld
					if(TileRummyMain.field.get(i).size() != p2NewBoard.get(i).size()) {
						TileRummyMain.field = p2NewBoard; //replace old board with this new one
						return;
					}
				}
			}
			returnCardsAndDrawCard(p2Melds);
		}
	}
	
	//return cards to hand and draw a card
	public void returnCardsAndDrawCard(ArrayList<ArrayList<Tile>> p2Melds) {
		//return unPlayed Melds back into Hand
		for(int i = 0; i < p2Melds.size(); i++) { //double loops in and adds all Tiles back
			for(int j = 0; j < p2Melds.get(i).size(); j++) {
				player.addTile(p2Melds.get(i).get(j));
			}
		}
		//draw a Tile
		if(!TileRummyMain.initDeck.isEmpty()){ //if not empty NORMAL CASE
			System.out.println("Player using Strat2 draws: " + TileRummyMain.initDeck.get(0));
			player.drawTile(TileRummyMain.initDeck);
		}
		else { //just when doing partial tests
			TileRummyMain.initDeck = TileRummyMain.buildDeck(TileRummyMain.suites, TileRummyMain.values);
			System.out.println("Player using Strat2 draws: " + TileRummyMain.initDeck.get(0));
			player.drawTile(TileRummyMain.initDeck);
		}
	}
	
	public void setPlayer(AI thisPlayer) {
		player = thisPlayer;
	}
}
