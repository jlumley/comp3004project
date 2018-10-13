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
			
		 	Tile t1 = new Tile("B", 1);
			testPlayer.addTile(t1);
			testHand.add(t1);
			assertEquals(true, testHand.equals(testPlayer.getHand()));
			
			Tile t2 = new Tile("G", 4);
			testPlayer.addTile(t2);
			testHand.add(t2);
			assertEquals(true, testHand.equals(testPlayer.getHand()));
			
			Tile t3 = new Tile("R", 10);
			testPlayer.addTile(t3);
			testHand.add(t3);
			assertEquals(true, testHand.equals(testPlayer.getHand()));
			
			Tile t4 = new Tile("Y", 13);
			testPlayer.addTile(t4);
			testHand.add(t4);
			assertEquals(true, testHand.equals(testPlayer.getHand()));	
			
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
			
			testPlayer.addTile(new Tile("Y", 12));
			assertEquals(1, testPlayer.countTiles());
			
			testPlayer.addTile(new Tile("B", 9));
			assertEquals(2, testPlayer.countTiles());
			
			testPlayer.addTile(new Tile("G", 3));
			assertEquals(3, testPlayer.countTiles());
		}
		
		
}
