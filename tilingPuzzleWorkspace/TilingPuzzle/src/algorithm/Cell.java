package algorithm;

public class Cell
{
	private int i;	//denotes which row
	private int j; //denotes which column
	
	Cell(int rowNum, int colNum)
	{
		i = rowNum;
		j = colNum;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
	@Override
	public String toString()
	{
		return "(" + i + "," + j + ")";
	}
}
