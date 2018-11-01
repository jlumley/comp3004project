package core;

import java.util.ArrayList;

public class Strategy1 implements AIStrategy{
	public void playTurn() {
		ArrayList<ArrayList<Tile>> p1Melds = TileRummyMain.player1.playAllTiles();
		//Check for first turn 30 points
			//if not >30 put all tiles back in Player's Hand & draw Card
		
		//System.out.println("Possible Melds in Hand: " + p1Melds);
		if(TileRummyMain.player1.checkPlays(p1Melds)) {
			for(int i = 0; i < p1Melds.size(); i++) {
				TileRummyMain.addMend(p1Melds.get(i));
			}
		}
		//Play all the tiles it can
		
	}
}
