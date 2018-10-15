package core;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import junit.framework.TestCase;

public class PlayerTest extends TestCase
{
		public void testaddTileAndSortHand()
		{
			// testing that the cards added were actually added to their hand
			Player testPlayer = new Player();
			ArrayList<Tile> testHand = new ArrayList<Tile>();
			
		 	Tile t1 = new Tile("B", 2);
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
			
			Tile t5 = new Tile("B", 1);
			testPlayer.addTile(t5);
			testHand.add(0, t5);
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
		
	
		public void testInHandMelds() {
			Player testPlayer = new Player();
			ArrayList<ArrayList<Tile>> possibleMelds = new ArrayList<ArrayList<Tile>>();
			assertEquals(possibleMelds, testPlayer.inHandMelds());
			
			ArrayList<Tile> temp = new ArrayList<Tile>();
			Tile t1 = new Tile("B", 10);
			testPlayer.addTile(t1);
			temp.add(t1);
			Tile t2 = new Tile("G", 10);
			testPlayer.addTile(t2);
			temp.add(t2);
			Tile t3 = new Tile("Y", 10);
			testPlayer.addTile(t3);
			Tile t111 = new Tile("Y", 11);
			testPlayer.addTile(t111);
			temp.add(t3);
			possibleMelds.add(temp);
			//assertEquals(possibleMelds, testPlayer.inHandMelds());
			
			temp = new ArrayList<Tile>();
			Tile t4 = new Tile("G", 3);
			testPlayer.addTile(t4);
			temp.add(t4);
			Tile t5 = new Tile("G", 4);
			testPlayer.addTile(t5);
			temp.add(t5);
			Tile t6 = new Tile("G", 5);
			testPlayer.addTile(t6);
			temp.add(t6);
			possibleMelds.add(temp);
			//assertEquals(possibleMelds, testPlayer.inHandMelds());
			
			Tile t7 = new Tile("G", 2);
			testPlayer.addTile(t7);
			temp.add(0, t7);
			possibleMelds.remove(1);
			possibleMelds.add(temp);
			assertEquals(possibleMelds, testPlayer.inHandMelds());

			
		}
		
		
}
