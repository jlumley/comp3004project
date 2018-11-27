package core;

import java.util.ArrayList;

import junit.framework.TestCase;


public class mainTest extends TestCase{
	public void testMain1() {
		TileRummyMain result = new TileRummyMain();
		
		ArrayList<Tile> temp = new ArrayList<Tile>(); // set
		ArrayList<ArrayList<Tile>> temp1 = new ArrayList<ArrayList<Tile>>();
		Tile t1 = new Tile("B", 10);
		temp.add(t1);
		Tile t2 = new Tile("O", 10);
		temp.add(t2);
		Tile t3 = new Tile("R", 10);
		temp.add(t3);
		temp1.add(temp);
		ArrayList<Tile> temp2 = new ArrayList<Tile>(); // run
		Tile t4 = new Tile("B", 6); 
		temp2.add(t4);
		Tile t5 = new Tile("B", 7);
		temp2.add(t5);
		Tile t6 = new Tile("B", 5);
		temp2.add(t6);
		temp1.add(temp2);
		result.initialize();
		result.playGame();
		result.showDeck();
		result.player0.showHand();
		//result.drawTile();
		result.player0.showHand();
		result.showField();
		result.getHandSize();
		assertEquals(false,result.checkPlays(temp1));
		//assertEquals(false,result.player0.checkPlays(temp1)); // checks for addMend as well
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
		assertEquals(14, result.player0.getHand().size());
		assertEquals(14, result.player1.getHand().size());
		assertEquals(14, result.player2.getHand().size());
		//assertEquals(14, result.player3.getHand().size());
	}
	
	public void testGetHandSizeOfOtherPlayers() {
		TileRummyMain result = new TileRummyMain();
		result.resetStaticVars();
		assertEquals(0, result.getHandSizeOfOtherPlayers(result.player3)[0]);
		assertEquals(0, result.getHandSizeOfOtherPlayers(result.player3)[1]);
		assertEquals(0, result.getHandSizeOfOtherPlayers(result.player3)[2]);
		result.initialize();
		assertEquals(14, result.getHandSizeOfOtherPlayers(result.player3)[0]);
		assertEquals(14, result.getHandSizeOfOtherPlayers(result.player3)[1]);
		assertEquals(14, result.getHandSizeOfOtherPlayers(result.player3)[2]);
		result.player1.addTile(new Tile("O", 10));
		assertEquals(15, result.getHandSizeOfOtherPlayers(result.player3)[1]);
	}
	
	public void testValidTable() {
		ArrayList<ArrayList<Tile>> table = new ArrayList<ArrayList<Tile>>();
		ArrayList<Tile> temp = new ArrayList<Tile>();
		temp.add(new Tile("O", 1));
		temp.add(new Tile("G", 1));
		temp.add(new Tile("R", 1));
		table.add(temp);
		
		temp = new ArrayList<Tile>();
		temp.add(new Tile("B", 1));
		temp.add(new Tile("B", 2));
		temp.add(new Tile("B", 3));
		table.add(temp);
		
		assertEquals(true, TileRummyMain.isValidTable(table));
		
		table = new ArrayList<ArrayList<Tile>>();
		temp = new ArrayList<Tile>();
		temp.add(new Tile("O", 1));
		temp.add(new Tile("O", 1));
		temp.add(new Tile("R", 1));
		table.add(temp);
		
		temp = new ArrayList<Tile>();
		temp.add(new Tile("B", 1));
		temp.add(new Tile("B", 2));
		temp.add(new Tile("B", 3));
		table.add(temp);
		
		assertEquals(false, TileRummyMain.isValidTable(table));
		
		table = new ArrayList<ArrayList<Tile>>();
		temp = new ArrayList<Tile>();
		temp.add(new Tile("O", 1));
		temp.add(new Tile("G", 1));
		temp.add(new Tile("R", 1));
		table.add(temp);
		
		temp = new ArrayList<Tile>();
		temp.add(new Tile("B", 1));
		temp.add(new Tile("G", 2));
		temp.add(new Tile("B", 3));
		table.add(temp);
		
		assertEquals(false, TileRummyMain.isValidTable(table));
	}
}