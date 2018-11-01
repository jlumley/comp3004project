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
		System.out.println("Player1 Hand: ");
		result.player1.showHand();
		System.out.println("Field: ");
		result.showField();
		
		result.player1.playTurn();
		
		System.out.println("Player1 Hand: ");
		result.player1.showHand();
		System.out.println("Field: ");
		result.showField();
	}
	//Works when TileRummyMain dealHands() deals to Player3
	public void testExistsAComparativelySmallHand() {
		TileRummyMain result = new TileRummyMain();
		result.initialize();
		Strategy3 strat3 = new Strategy3();
		assertEquals(false, strat3.existsAComparativelySmallHand());
		result.player3.addTile(new Tile("B", 2));
		result.player3.addTile(new Tile("B", 3));
		assertEquals(false, strat3.existsAComparativelySmallHand());
		result.player3.addTile(new Tile("R", 3));
		assertEquals(true, strat3.existsAComparativelySmallHand());
	}
}
