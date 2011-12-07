package algorithm;

import java.util.ArrayList;

public class ColumnComparator
{

	public static boolean compare(ArrayList<Integer> list1, ArrayList<Integer> list2) 
	{
		boolean isEqual = true;
		for(int list1lcv: list1)
		{
			for(int list2lcv : list2)
			{
				if(list1lcv != list2lcv )
				{
					isEqual = false;
					break;
				}
			}
		}
		
		return isEqual;
	}

}
