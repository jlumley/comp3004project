package core;

import java.util.ArrayList;

import junit.framework.TestCase;


public class mainTest extends TestCase{
	public void testMain1() {
		TileRummyMain result = new TileRummyMain();
		
		ArrayList<Tile> temp = new ArrayList<Tile>();
		Tile t1 = new Tile("B", 10);
		temp.add(t1);
		Tile t2 = new Tile("Y", 10);
		temp.add(t2);
		Tile t3 = new Tile("B", 10);
		temp.add(t3);
		
		result.initialize();
		result.playGame();
		result.showDeck();
		result.player0.showHand();
		result.showField();
		result.getHandSize();
		assertEquals(true,result.checkSizeofMendHand(temp)); // checks for addMend as well
		result.showField();
		assertEquals(false,result.checkGameStatus()); //checks if any user has an empty hand
	}
	public void testBuildDeck() {
		TileRummyMain result = new TileRummyMain();
		assertNotNull(result.buildDeck(result.suites, result.values));
		assertEquals(104, result.buildDeck(result.suites, result.values).size());
		
	}
	public void testdealHands() {
		TileRummyMain result = new TileRummyMain();
		result.initialize();
		assertNotNull(result.player0.getHand());
		assertNotNull(result.player1.getHand());
		assertNotNull(result.player2.getHand());
		assertNotNull(result.player3.getHand());
		assertEquals(13, result.player0.getHand().size());
		assertEquals(13, result.player1.getHand().size());
		assertEquals(13, result.player2.getHand().size());
		assertEquals(0, result.player3.getHand().size());
	}
	
}