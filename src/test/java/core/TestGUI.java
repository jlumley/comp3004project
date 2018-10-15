package core;

import org.junit.Test;

import junit.framework.TestCase;

public class TestGUI extends TestCase
{
	@Test
	public void testDealHand()
	{
		GUI gui = new GUI();
		
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		
		/* Create tiles to add to hands */
		Tile r4 = new Tile("R", 4);
		Tile r5 = new Tile("R", 5);
		
		Tile y2 = new Tile("Y", 2);
		Tile y3 = new Tile("Y", 3);
		
		Tile g6 = new Tile("G", 6);
		Tile g7 = new Tile("G", 7);
		
		Tile b8 = new Tile("B", 8);
		Tile b9 = new Tile("B", 9);
		
		/* Add cards to hand */
		player1.addTile(r4);
		player1.addTile(r5);
		
		player2.addTile(y2);
		player2.addTile(y3);
		
		player3.addTile(g6);
		player3.addTile(g7);
		
		player4.addTile(b8);
		player4.addTile(b9);
		
		/* This functions will give each player and AI there hand */
		assertEquals(GUI.dealHand(player1.getHand(), player2.getHand(), player3.getHand(), player4.getHand()), true);
	}
	
	@Test
	public void testDisplayMessage()
	{
		GUI gui = new GUI();
		
		/* This will be used when AI said there action and serves as a message dispatch for anything else */
		assertEquals(1, 2);
	}
	@Test
	public void testDisplayWinner()
	{
		/* Show winner this will happen at end of game */
		assertEquals(1, 2);
	}
	
	//TODO
	//Make test and implementations to deal with auto attaching to melds
	@Test
	public void testCheckBounds()
	{
		/* if tile is out of bounds when released put it back */
	}
	@Test
	public void testAutoMeld()
	{
		/* auto attach to melds */
		assertEquals(1, 2);
	}
	@Test
	public void testCheckDrag()
	{
		/* Check if user is allowed to drag tile then drag the tile */
	}
}