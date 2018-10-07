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
			//Check each colour Red, Green, Blue, Black
			Tile testTile = new Tile();
			assertEquals(testTile.setColour("Green"), true);
			assertEquals(testTile.getColour(), "Green");
			
			assertEquals(testTile.setColour("Blue"), true);
			assertEquals(testTile.getColour(), "Blue");
			
			assertEquals(testTile.setColour("Black"), true);
			assertEquals(testTile.getColour(), "Black");
			
			assertEquals(testTile.setColour("Red"), true);
			assertEquals(testTile.getColour(), "Red");
		}
		@Test
		public void TestGetFileImage()
		{
			assertEquals(1, 2);
		}
		@Test
		public void TestSetValue()
		{
			/* Check values 1 to 13 */
			for(int i = 1; i < 14; i++)
			{
				assertEquals(testTile.setValue(i), false);
				assertEquals(testTile.getValue(), i);
			}
		}
		@Test
		public void TestSetColour()
		{
			/* check each colour */
			assertEquals(testTile.setColour("Green"), true);
			assertEquals(testTile.setColour("Blue"), true);
			assertEquals(testTile.setColour("Red"), true);
			assertEquals(testTile.setColour("Black"), true);
			assertEquals(testTile.setColour("Not a colour"), false);
		}
		@Test
		public void TestSetFileImage()
		{
			assertEquals(testTile.setFileImage("Green10"), true);
			assertEquals(testTile.setFileImage("Red09"), true);
			assertEquals(testTile.setFileImage("Blue10"), true);
			assertEquals(testTile.setFileImage("Black09"), true);
			assertEquals(testTile.setFileImage("Not a image"), false);
		}
}
