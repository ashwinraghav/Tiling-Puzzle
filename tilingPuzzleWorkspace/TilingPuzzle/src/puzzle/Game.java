package puzzle;

import java.util.ArrayList;

public class Game 
{
	private ArrayList<Tile> tileList;
	private Tile board;
	
	public Game(ArrayList<Tile> tileList, Tile board)
	{
		this.tileList = tileList;
		this.board = board;
	}

	public ArrayList<Tile> getTileList() {
		return tileList;
	}

	public Tile getBoard() {
		return board;
	}
	
	
}
