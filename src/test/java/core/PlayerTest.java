package core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class PlayerTest
{
		@Test
		public void TestaddTile()
		{
			// testing that the cards added were actually added to their hand
			Player testPlayer = new Player();
			ArrayList<Tile> testHand = new ArrayList<Tile>();
			
			testPlayer.addTile("blue", 1);
			testHand.add(new Tile("blue", 1));
			assertEquals(testHand.getHand(), testHand);
			
			testPlayer.addTile("green", 4);
			testHand.add(new Tile("green", 4));
			assertEquals(testHand.getHand(), testHand);
			
			testPlayer.addTile("red", 10);
			testHand.add(new Tile("red", 10));
			assertEquals(testHand.getHand(), testHand);
			
			testPlayer.addTile("orange", 13);
			testHand.add(new Tile("orange", 13));
			assertEquals(testHand.getHand(), testHand);	
			
		}
}
