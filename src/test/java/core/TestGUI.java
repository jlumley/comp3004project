package core;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.TestCase;

public class TestGUI extends TestCase
{
	public void testPlaceDeck()
	{
		GUI gui = new GUI();
		ArrayList<Tile> deck = null;
		/* This functions will give each player and AI there hand */
		assertEquals(gui.placeDeck(deck), true);
	}
	
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
		
		Tile o2 = new Tile("O", 2);
		Tile o3 = new Tile("O", 3);
		
		Tile g6 = new Tile("G", 6);
		Tile g7 = new Tile("G", 7);
		
		Tile b8 = new Tile("B", 8);
		Tile b9 = new Tile("B", 9);
		
		/* Add cards to hand */
		player1.addTile(r4);
		player1.addTile(r5);
		
		player2.addTile(o2);
		player2.addTile(o3);
		
		player3.addTile(g6);
		player3.addTile(g7);
		
		player4.addTile(b8);
		player4.addTile(b9);
		
		/* This functions will give each player and AI there hand */
		assertEquals(gui.dealHand(player1.getHand(), player2.getHand(), player3.getHand(), player4.getHand()), true);
	}
	
	@Test
	public void testDisplayMessage()
	{
		GUI gui = new GUI();
		
		/* This will be used when AI said there action and serves as a message dispatch for anything else */
		assertEquals(gui.sayMsg("Game has started"), true);
	}
	@Test
	public void testDisplayWinner()
	{
		/* Show winner this will happen at end of game */	
		GUI gui = new GUI();
		assertEquals(gui.sayMsg("Game Winner is: "), true);
	}
}
