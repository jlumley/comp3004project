package core;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;

import junit.framework.TestCase;

public class PlayerTest extends TestCase
{
		public void testaddTile()
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
		
		public void testdrawTile()
		{
			// testing that the cards added were actually added to their hand
			Player testPlayer = new Player();
			ArrayList<Tile> testHand = new ArrayList<Tile>();
			ArrayList<Tile> collection = new ArrayList<Tile>();
			String[] colors = {"R", "G", "B", "Y"};
			
			for (String s: colors) {
				for (int i=1; i<=13; i++) {
					collection.add(new Tile(s, i));
				}
			}
			assertEquals(52, collection.size());
			
			testPlayer.drawTile(collection);
			assertEquals(1, testPlayer.getHand().size());
			assertEquals(51, collection.size());

			testPlayer.drawTile(collection);
			assertEquals(2, testPlayer.getHand().size());
			assertEquals(50, collection.size());
			
			testPlayer.drawTile(collection);
			assertEquals(3, testPlayer.getHand().size());
			assertEquals(49, collection.size());
			
			testPlayer.drawTile(collection);
			assertEquals(4, testPlayer.getHand().size());
			assertEquals(48, collection.size());
			
		}
		
		public void testcountTiles() {
			Player testPlayer = new Player();
			assertEquals(0, testPlayer.countTiles());
			
			testPlayer.addTile(new Tile("O", 12));
			assertEquals(1, testPlayer.countTiles());
			
			testPlayer.addTile(new Tile("B", 9));
			assertEquals(2, testPlayer.countTiles());
			
			testPlayer.addTile(new Tile("G", 3));
			assertEquals(3, testPlayer.countTiles());
		}
		
		
}
