package fileHandler;

public class InputMatrixInfo
{
	private int numRows;
	private int numCols;
	
	InputMatrixInfo(int numRows, int numCols)
	{
		this.numCols = numCols;
		this.numRows = numRows;
	}

	public int getNumRows() 
	{
		return numRows;
	}

	public int getNumCols() 
	{
		return numCols;
	}
}
