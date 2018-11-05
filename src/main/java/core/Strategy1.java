package core;

import java.util.ArrayList;

public class Strategy1 implements AIStrategy{
	public void playTurn() {
		ArrayList<ArrayList<Tile>> p1Melds = TileRummyMain.player1.playAllTiles();
			//if not >30 put all tiles back in Player's Hand & draw Card
		System.out.println("Player 1 Turn");
		System.out.println("Player 1 Hand: " + TileRummyMain.player1.getHand());
		System.out.println("Possible Melds in Hand: " + p1Melds);
		if(TileRummyMain.player1.checkPlays(p1Melds)) { //checks for >=30 size
			for(int i = 0; i < p1Melds.size(); i++) { //Play all the tiles it can
				TileRummyMain.addMend(p1Melds.get(i)); //add all melds to board
			}
			TileRummyMain.player1.firstTurn = true;
		}
		else { //returns any unused Tiles back to hand, and draws a Tile
			//return unPlayed Melds back into Hand
			for(int i = 0; i < p1Melds.size(); i++) { //double loops in and adds all Tiles back
				for(int j = 0; j < p1Melds.get(i).size(); j++) {
					TileRummyMain.player1.addTile(p1Melds.get(i).get(j));
				}
			}
			//draw a Tile
			if(!TileRummyMain.initDeck.isEmpty()){ //if not empty NORMAL CASE
				System.out.println("Player 1 draws: " + TileRummyMain.initDeck.get(0));
				TileRummyMain.player1.drawTile(TileRummyMain.initDeck);
			}
			else { //just when doing partial tests
				TileRummyMain.initDeck = TileRummyMain.buildDeck(TileRummyMain.suites, TileRummyMain.values);
				System.out.println("Player 1 draws: " + TileRummyMain.initDeck.get(0));
				TileRummyMain.player1.drawTile(TileRummyMain.initDeck);
			}
		}
		
	}
}
