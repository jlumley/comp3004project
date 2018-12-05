package core;

import java.util.ArrayList;
/*
 * Each AIPlayer needs to implement a Strategy
 * Each Strategy needs an instance of it's Player
*/
public class Strategy1 implements AIStrategy{
	private AI player; 
	
	public void playTurn() {
		System.out.println("Player using Strat1 Turn");
		System.out.println("Player using Strat1 Hand: " + player.getHand());
		ArrayList<ArrayList<Tile>> p1Melds = player.playAllTiles();
		System.out.println("Possible Melds in Hand: " + p1Melds);
		if(player.firstTurn == false) { //first turn has not passed yet
			if(player.checkPlays(p1Melds)) { //checks for >=30 size
				for(int i = 0; i < p1Melds.size(); i++) { //Play all the tiles it can
					TileRummyMain.addMend(p1Melds.get(i)); //add all melds to board
				}
				player.firstTurn = true;
			}
			else { //returns any unused Tiles back to hand, and draws a Tile
				returnCardsAndDrawCard(p1Melds);
			}
		}
		else {
			ArrayList<ArrayList<Tile>> p1NewBoard = player.playAllTiles(TileRummyMain.field);
			//Check if can win this turn, if so Win
			if(player.countTiles() == 0) {
				TileRummyMain.field = p1NewBoard; //replace old board with this new one
				for(int i = 0; i < p1Melds.size(); i++) { 
					TileRummyMain.addMend(p1Melds.get(i)); 
				}
				return;
			}
			//play all tiles including board reuse
			if(TileRummyMain.field.size() != p1NewBoard.size()) { //check num of melds
				TileRummyMain.field = p1NewBoard; //replace old board with this new one
				for(int i = 0; i < p1Melds.size(); i++) { 
					TileRummyMain.addMend(p1Melds.get(i)); 
				}
				return;
			}
			else { //if equal num melds
				for(int i = 0; i < TileRummyMain.field.size(); i++) { //check size of each meld
					if(TileRummyMain.field.get(i).size() != p1NewBoard.get(i).size()) {
						TileRummyMain.field = p1NewBoard; //replace old board with this new one
						for(int k = 0; k < p1Melds.size(); k++) { 
							TileRummyMain.addMend(p1Melds.get(k)); 
						}
						return;
					}
				}
			}
			//no board re-use available but try just regular meld playing
			if(!p1Melds.isEmpty()) {//not empty
				for(int i = 0; i < p1Melds.size(); i++) { //Play all the tiles it can
					TileRummyMain.addMend(p1Melds.get(i)); //add all melds to board
				}
				return;
			}
			//otherwise
			System.out.println("AIPlayer could play but has no tile to play");
			returnCardsAndDrawCard(p1Melds);
		}
		
	}
	//returns any unused Tiles back to hand, and draws a Tile
	public void returnCardsAndDrawCard(ArrayList<ArrayList<Tile>> p1Melds) {
		//return unPlayed Melds back into Hand
		for(int i = 0; i < p1Melds.size(); i++) { //double loops in and adds all Tiles back
			for(int j = 0; j < p1Melds.get(i).size(); j++) {
				player.addTile(p1Melds.get(i).get(j));
			}
		}
		//draw a Tile
		if(!TileRummyMain.initDeck.isEmpty()){ //if not empty NORMAL CASE
			System.out.println("Player using Strat1 draws: " + TileRummyMain.initDeck.get(0));
			player.drawTile(TileRummyMain.initDeck);
		}
		else { //just when doing partial tests
			TileRummyMain.initDeck = TileRummyMain.buildDeck(TileRummyMain.suites, TileRummyMain.values);
			System.out.println("Player using Strat1 draws: " + TileRummyMain.initDeck.get(0));
			player.drawTile(TileRummyMain.initDeck);
		}
	}
	
	public void setPlayer(AI thisPlayer) {
		player = thisPlayer;
	}
}
