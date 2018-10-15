package core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


import junit.framework.TestCase;

public class TestTile extends TestCase
{
		@Test
		public void testGetValue()
		{
			Tile testTile = new Tile("", 0);
			
			/* Check extreme values */
			assertEquals(testTile.setValue(-1), false);
			assertEquals(testTile.setValue(10), true);
			assertEquals(testTile.setValue(1000), false);
			assertEquals(testTile.getValue(), 10);
		}
		@Test
		public void testGetColour()
		{
			Tile testTile = new Tile("", 0);
			
			//Check each colour Red, Green, Blue, Black
			assertEquals(testTile.setColour("green"), true);
			assertEquals(testTile.getColour(), "G");
			
			assertEquals(testTile.setColour("blue"), true);
			assertEquals(testTile.getColour(), "B");
			
			assertEquals(testTile.setColour("yellow"), true);
			assertEquals(testTile.getColour(), "Y");
			
			assertEquals(testTile.setColour("red"), true);
			assertEquals(testTile.getColour(), "R");
			
			assertEquals(testTile.setColour("blue"), true);
			assertEquals(testTile.getColour(), "B");
			
			assertEquals(testTile.setColour("Yellow"), true);
			assertEquals(testTile.getColour(), "Y");
			
			assertEquals(testTile.setColour("Red"), true);
			assertEquals(testTile.getColour(), "R");
		}
		
		@Test
		public void testTileConstructor() {
			Tile testTile = new Tile("Yellow", 13);
			assertEquals(testTile.getValue(), 13);
			assertEquals("Y", testTile.getColour());
			
			testTile = new Tile("blue", 1);
			assertEquals(testTile.getValue(), 1);
			assertEquals("B", testTile.getColour());
		}
		/* @Test
		public void testGetFileImage()
		{
			Tile testTile = new Tile();
			assertEquals(1, 2);
		} */
		
		@Test
		public void testSetValue()
		{
			Tile testTile = new Tile("", 0);
			/* Check values 1 to 13 */
			for(int i = 1; i < 14; i++)
			{
				assertEquals(testTile.setValue(i), true);
				assertEquals(testTile.getValue(), i);
			}
		}
		@Test
		public void testSetColour()
		{
			Tile testTile = new Tile("", 0);
			/* check each colour */
			assertEquals(testTile.setColour("green"), true);
			assertEquals(testTile.setColour("blue"), true);
			assertEquals(testTile.setColour("red"), true);
			assertEquals(testTile.setColour("yellow"), true);

			assertEquals(testTile.setColour("Blue"), true);
			assertEquals(testTile.setColour("Red"), true);
			assertEquals(testTile.setColour("Yellow"), true);
			assertEquals(testTile.setColour("R"), true);
			assertEquals(testTile.setColour("Blfdsafji"), true); //turns to Blue
			assertEquals(testTile.setColour("Not a colour"), false);
		}
		
		@Test
		public void testToString() {
			Tile testTile = new Tile("Y", 7);
			assertEquals("Y7", testTile.toString());
			testTile.setValue(11);
			assertEquals("Y11", testTile.toString());
		}
		@Test
		public void testSetFileImage()
		{
			Tile testTile = new Tile("B", 4);
			assertEquals(testTile.setFileImage("TileB10.jpg"), true);
			assertEquals(testTile.setFileImage("TileR9.jpg"), true);
			assertEquals(testTile.setFileImage("TileB10.jpg"), true);
			assertEquals(testTile.setFileImage("TileO9.jpg"), true);
		}
		
		@Test
		public void testCompareTo() {
			assertEquals(0, new Tile("B", 4).compareTo(new Tile("B", 4)));
			assertEquals(23, new Tile("Y", 4).compareTo(new Tile("B", 4)));
			assertEquals(-23, new Tile("B", 4).compareTo(new Tile("Y", 4)));
			assertEquals(-1, new Tile("B", 4).compareTo(new Tile("B", 6)));
			assertEquals(1, new Tile("B", 6).compareTo(new Tile("B", 4)));
		}
}
