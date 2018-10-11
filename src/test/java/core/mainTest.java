package core;

import junit.framework.TestCase;


public class mainTest extends TestCase{
	public void testMain1() {
		TileRummyMain result = new TileRummyMain();
		result.initialize();
		result.showDeck();
		result.showHands();
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
		assertEquals(14, result.player0.getHand().size());
		assertEquals(14, result.player1.getHand().size());
		assertEquals(14, result.player2.getHand().size());
		assertEquals(14, result.player3.getHand().size());
	}
	
}