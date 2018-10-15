package core;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestTile
{
		@Test
		public void TestGetValue()
		{
			Tile testTile = new Tile();
			
			/* Check extreme values */
			assertEquals(testTile.setValue(-1), false);
			assertEquals(testTile.setValue(10), true);
			assertEquals(testTile.setValue(1000), false);
			assertEquals(testTile.getValue(), 10);
		}
		@Test
		public void TestGetColour()
		{
			Tile testTile = new Tile();
			
			//Check each colour Red, Green, Blue, Black
			assertEquals(testTile.setColour("green"), true);
			assertEquals(testTile.getColour(), "green");
			
			assertEquals(testTile.setColour("blue"), true);
			assertEquals(testTile.getColour(), "blue");
			
			assertEquals(testTile.setColour("orange"), true);
			assertEquals(testTile.getColour(), "orange");
			
			assertEquals(testTile.setColour("red"), true);
			assertEquals(testTile.getColour(), "red");
		}
		
		/* @Test
		public void TestGetFileImage()
		{
			Tile testTile = new Tile();
			assertEquals(1, 2);
		} */
		
		@Test
		public void TestSetValue()
		{
			Tile testTile = new Tile();
			/* Check values 1 to 13 */
			for(int i = 1; i < 14; i++)
			{
				assertEquals(testTile.setValue(i), true);
				assertEquals(testTile.getValue(), i);
			}
		}
		@Test
		public void TestSetColour()
		{
			Tile testTile = new Tile();
			/* check each colour */
			assertEquals(testTile.setColour("green"), true);
			assertEquals(testTile.setColour("blue"), true);
			assertEquals(testTile.setColour("red"), true);
			assertEquals(testTile.setColour("orange"), true);
			assertEquals(testTile.setColour("Not a colour"), false);
		}
		/*@Test
		public void TestSetFileImage()
		{
			Tile testTile = new Tile();
			assertEquals(testTile.setFileImage("Green10"), true);
			assertEquals(testTile.setFileImage("Red09"), true);
			assertEquals(testTile.setFileImage("Blue10"), true);
			assertEquals(testTile.setFileImage("Black09"), true);
			assertEquals(testTile.setFileImage("Not a image"), false);
		}*/
}
