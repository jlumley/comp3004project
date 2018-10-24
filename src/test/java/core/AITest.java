package core;

import junit.framework.TestCase;

public class AITest extends TestCase{
	public void testAddToField() { //tests if adding given tiles to the board could have a valid board
		
	}
	public void testValidField() { //checks if the board contains all valid melds without extra tiles
		
	}
	public void testAIPlayer1() {
		TileRummyMain result = new TileRummyMain();
		result.initialize();
		result.player1.showHand();
		result.showField();
		result.player1.playTurn();
		result.player1.showHand();
		result.showField();
	}
}
