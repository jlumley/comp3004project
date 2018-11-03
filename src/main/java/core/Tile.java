package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class Tile implements Comparable<Tile>
{
	private static final String imageDir = "src/main/resources/core/tiles/";
	private int value;
	private String colour;
	private boolean enableMove;
	private static int id_count = 0;
	private int id;

	private Image tileImage;
	private float x;
	private float y;
	
	public boolean joker = false;
	public String getColour() { return colour;}
	public int getValue() { return value;}
	public String getSuite() { return colour;}
	public float getX() {return x;}
	public float getY() {return y;}
	

	public Tile(String tileColour, int value) 
	{
		//Initialize coordinates
		x = 0;
		y = 0;
		
		if(value == 99)
		{ 
			// setting it up as a dummy checker for Jokers, need team input
			this.joker = true;
		}
		else 
		{
			setValue(value);
		}
		
		setColour(tileColour);
		this.id = id_count;
		this.id_count += 1;

		//Tiles are expected to be in the format "Tile" + firstLetterOfColour + Value 
		//for example TileB10 (tile blue 10 )or TileR2 (tile red 2) 
		setFileImage("Tile" + this.colour + String.valueOf(value) + ".jpg");

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
		

		if(!tempColour.equals("R") & !tempColour.equals("B") & !tempColour.equals("G") & !tempColour.equals("O") & !tempColour.equals("X"))

			return false;
		
		//TODO remove this once we decide if we use orange or yellow
		if(tempColour.equals("Y"))
			tempColour = "O";
		
		this.colour = tempColour;
		return true;
	}
	
	public boolean setFileImage(String fileName) 
	{
		try 
		{
			Image cardImage;
			cardImage = new Image(new FileInputStream(imageDir + fileName));
			tileImage = cardImage;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;	
	}
	
	public Image getImage()
	{
		return tileImage;
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
		if (this.colour == o.colour) {
			return this.value - o.value;
		} else {
			return this.colour.compareTo(o.colour);
		}
	}
	public boolean setx(float pos) 
	{
		if(pos > 1000 || pos < 0)
			return false;
		x = pos;
		return true;
	}
	
	public boolean sety(float pos) 
	{
		if(pos > 1000 || pos < 0)
			return false;
		y = pos;
		return true;
	}
}
