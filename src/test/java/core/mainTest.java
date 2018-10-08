package core;

import junit.framework.TestCase;


public class mainTest extends TestCase{
	public void testMain1() {
		TileRummyMain result = new TileRummyMain();
		result.initialize();
		result.showDeck();
		result.showHands();
	}
	
}