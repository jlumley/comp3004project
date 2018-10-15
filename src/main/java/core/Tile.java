package core;

import java.io.File;

public class Tile 
{
	private int value;
	private String colour;
	private static final String imageDir = "src/main/resources/core/cards/";
	private File tileImage;
	public String getColour() { return colour;}
	public int getValue() { return value;}
	
	public boolean setValue(int tempValue) 
	{
		if( 13 < tempValue || tempValue < 1)
			return false;
		this.value = tempValue;
		return true;
	}

	public boolean setColour(String tempColour) 
	{
		if(tempColour != "orange" & tempColour != "blue" & tempColour != "red" & tempColour != "green")
			return false;
		
		this.colour = tempColour;
		return true;
	}
	
	public boolean setFileImage(String fileName) 
	{
		File file = new File(imageDir + fileName);
		tileImage = file;
		
		if(tileImage != null)
		{
			return true;
		}
		else
		{
			return false;
		}	
	}
}
