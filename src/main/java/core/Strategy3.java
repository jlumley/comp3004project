package core;

public class Strategy3 implements AIStrategy{
	
	public void playTurn() {
		//Check for 30 point limit and play it
		
		//Check if can win this turn, if so Win
		
		//(Observer) Ask game for the Hand sizes of all other players
	
		//If This has +3 than any of them: Play all Tiles possible
		//Else: keep all in-hand melds, and try adding to existing melds on board
	}
	
	public boolean existsAComparativelySmallHand() { // 3 or less Tiles then in this Player's Hand
		int[] handSizes = TileRummyMain.getHandSizeOfOtherPlayers(TileRummyMain.player3); //get HandSizes
		for(int i = 0; i < handSizes.length; i++) { 
			if(TileRummyMain.player3.getHand().size() >= (handSizes[i] + 3)) {
				return true;
			}
		}
		return false;
	}
	
}
