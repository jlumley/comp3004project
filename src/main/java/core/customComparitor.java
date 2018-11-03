package core;

import java.util.Comparator;

public class customComparitor implements Comparator<Tile>
{
	@Override
	public int compare(Tile tile1, Tile tile2) 
	{
		if(tile1.getValue() < tile2.getValue())
		{
			return -1;
		}
		else if(tile1.getValue() == tile2.getValue())
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
}


