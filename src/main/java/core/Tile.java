package core;

import java.io.File;

public class Tile implements Comparable<Tile>
{
	private int value;
	private String colour;
	private static int id_count = 0;
	private int id;
	private static final String imageDir = "src/main/resources/core/cards/";
	private File tileImage;
	public String getColour() { return colour;}
	public int getValue() { return value;}
	
	public Tile(String tileColour, int value) {
		setValue(value);
		setColour(tileColour);
		this.id = id_count;
		this.id_count += 1;
		//TODO Set Image
	}
	
	public boolean setValue(int tempValue) 
	{
		if( 13 < tempValue || tempValue < 1)
			return false;
		this.value = tempValue;
		return true;
	}

	public boolean setColour(String tempColour) 
	{
		tempColour.trim(); 
		tempColour = tempColour.toUpperCase(); //case sensitive is handled
		if(!tempColour.isEmpty()) {
			tempColour = tempColour.substring(0,1);} //change to single letter form
		if(!tempColour.equals("R") & !tempColour.equals("B") & !tempColour.equals("G") & !tempColour.equals("Y"))
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
	
	public String toString() {
		return colour + value;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public int compareTo(Tile o) {
		if (o == null) {
			return 0; 
		}
		if (this.value == o.value) {
			return this.colour.compareTo(o.colour);
		} else {
			return Integer.compare(this.value, o.value);
		}
	}
}
