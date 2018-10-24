package core;

import java.util.ArrayList;

public class Strategy1 implements AIStrategy{
	public void playTurn() {
		//Check for first turn 30 points
		ArrayList<ArrayList<Tile>> p1Melds = TileRummyMain.player1.inHandMelds();
		//System.out.println("Possible Melds in Hand: " + p1Melds);
		if(TileRummyMain.player1.checkPlays(p1Melds)) {
			for(int i = 0; i < p1Melds.size(); i++) {
				TileRummyMain.addMend(p1Melds.get(i));
			}
		}
		//Play all the tiles it can
		
	}
}
