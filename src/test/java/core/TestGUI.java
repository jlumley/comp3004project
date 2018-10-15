package core;

import org.junit.Test;

import junit.framework.TestCase;

public class TestGUI extends TestCase
{
	@Test
	public void testDealHand()
	{
		/* This functions will give each player and AI there hand */
		assertEquals(1, 2);
	}
	@Test
	public void testDragTile()
	{
		/* Check if user is allowed to drag tile then drag the tile */
		/* if tile is outof bounds when released put it back */
		/* auto attach to melds */
		assertEquals(1, 2);
	}
	@Test
	public void testDisplayMessage()
	{
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
}
