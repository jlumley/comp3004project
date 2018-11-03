package core;

import java.util.ArrayList;

public class Strategy1 implements AIStrategy{
	public void playTurn() {
		ArrayList<ArrayList<Tile>> p1Melds = TileRummyMain.player1.playAllTiles();
			//if not >30 put all tiles back in Player's Hand & draw Card
		
		//System.out.println("Possible Melds in Hand: " + p1Melds);
		if(TileRummyMain.player1.checkPlays(p1Melds)) { //checks for >=30 size
			for(int i = 0; i < p1Melds.size(); i++) {
				TileRummyMain.addMend(p1Melds.get(i)); //add all melds to board
			}
		}
		//Play all the tiles it can
		
	}
}
