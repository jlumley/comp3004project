package core;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import java.util.HashSet;

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
			assertEquals(testHand, testPlayer.getHand());
			
			testPlayer.addTile("green", 4);
			testHand.add(new Tile("green", 4));
			assertEquals(testHand, testPlayer.getHand());
			
			testPlayer.addTile("red", 10);
			testHand.add(new Tile("red", 10));
			assertEquals(testHand, testPlayer.getHand());
			
			testPlayer.addTile("orange", 13);
			testHand.add(new Tile("orange", 13));
			assertEquals(testHand, testPlayer.getHand());	
			
		}
		
		@Test
		public void TestdrawTile()
		{
			// testing that the cards added were actually added to their hand
			Player testPlayer = new Player();
			ArrayList<Tile> testHand = new ArrayList<Tile>();
			ArrayList<Tile> collection = new ArrayList<Tile>();
			HashSet<String> colors = new HashSet<String>();
			
			for (String s: colors) {
				for (int i=1; i<=13; i++) {
					collection.add(new Tile(s, i));
				}
			}
			
			testPlayer.drawTile(collection);
			assertEquals(1, collection.size());

			testPlayer.drawTile(collection);
			assertEquals(2, collection.size());
			
			testPlayer.drawTile(collection);
			assertEquals(3, collection.size());
			
			testPlayer.drawTile(collection);
			assertEquals(4, collection.size());
			
			
		}
		
		
}
