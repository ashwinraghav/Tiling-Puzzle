package algorithm.dancinglinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import puzzle.Domino;
import puzzle.Tile;
import utility.Printing;
import zegame.Table;

public class DancingLinksImpl
{

	static int attempt = 0;
	//Method initialising the top row for DancingLinks problem
	public static void initializeTop(DataObject header, int colSize, ArrayList<DataObject> topPointerList )
	{
		
		DataObject current = header;
		int len = colSize;
		int name = 0;
		
		for(int j=0; j < len; ++j)
		{
			DataObject tmp = new DataObject(Integer.toString(name));			
			name += 1;
			
			current.right = tmp;
			tmp.left = current;
			current = tmp;
			
			if(j+1 >= len)
			{
				tmp.right = header;
				header.left = tmp;
			}	
			
			topPointerList.add(j, tmp);
		}								
	}
	

	
	public static void initializeGrid(ArrayList<Tile> tileList, Tile board, Domino[][] matrix, HashMap< Tile, Set<ArrayList<Integer>> > mapping ) 
	{
		/*TODO: This algorithm is naive - brute-force 
		*/
		
		/*int nRows = board.getyDimension();
		int nCols = board.getxDimension();
		int i,j,k,l, lcv1, lcv2;	//loop-control variables
		int boardStartI = board.getStartingCell().getI();
		int boardStartJ = board.getStartingCell().getJ();
*/		
		for(Tile tile : tileList)	//I take each tile; infer its corresponding matrix and try to match
		{
/*			boolean flag = true;
 			
*/
			int xDim = tile.getxDimension();
			int yDim = tile.getyDimension();
			
			int tileStartI = tile.getStartingCell().getI();
			int tileStartJ = tile.getStartingCell().getJ();
			ArrayList<ArrayList<Integer>> collectionOfColumnIDs = new ArrayList<ArrayList<Integer>>();
			Set<ArrayList<Integer>> collectionOfColumnIDset = new HashSet<ArrayList<Integer>>();
			//call method with the two matrices, starting point of the tile matrix and the board
			
			collectionOfColumnIDset = (matchTileMatrixWithBoardMatrix(matrix, matrix, tileStartI, tileStartJ, board, xDim, yDim));
				
		/*	for(lcv1 = boardStartI; lcv1 <= (nRows - yDim) + boardStartI ; lcv1++)
			{
				for(lcv2= boardStartJ; lcv2 <= (nCols - xDim) + boardStartJ ; lcv2++)
				{
					ArrayList<Integer> columnId = new ArrayList<Integer>(xDim*yDim); 
					
					for(i= lcv1, k= tileStartI; i<nRows + boardStartI && k < yDim + tileStartI && flag == true; i++,k++ )
					{
						for(j = lcv2, l = tileStartJ; j<nCols + boardStartJ && l < xDim + tileStartJ && flag == true; j++, l++)
						{
							
							if(matrix[k][l] == null)	//empty cell
								continue;	
				
							if(matrix[k][l].equals(matrix[i][j]))
							{
								//get ColumnId
								columnId.add(new Integer(matrix[i][j].getId()));
								continue;
							}
							
							flag = false;
							
						}
						
					}
					
					if(flag == true)
					{			
						collectionOfColumnIDs.add(columnId);
					}
					
					flag = true;
				}
				
			}*/
			mapping.put(tile, collectionOfColumnIDset);	//this is the list of columnIDs for extraCover which a given tile-matrix matches 
	
		}
		
	}

	public static Set<ArrayList<Integer>>  matchTileMatrixWithBoardMatrix(
			Domino[][] matrix, Domino[][] boardMatrix,
			int tileStartI, int tileStartJ, Tile board, int xDim, int yDim) 
	{
		boolean flag = true;
		int boardStartI = board.getStartingCell().getI();
		int boardStartJ = board.getStartingCell().getJ();
		int nRows = board.getyDimension();
		int nCols = board.getxDimension();
		ArrayList<ArrayList<Integer>> collectionOfColumnIDs = new ArrayList<ArrayList<Integer>>();
		Set<ArrayList<Integer>> collectionOfColumnIDsSet = new HashSet<ArrayList<Integer>>();
		
		for(int lcv1 = boardStartI; lcv1 <= (nRows - yDim) + boardStartI ; lcv1++)
		{
			for(int lcv2= boardStartJ; lcv2 <= (nCols - xDim) + boardStartJ ; lcv2++)
			{
				ArrayList<Integer> columnId = new ArrayList<Integer>(xDim*yDim); 
				
				for(int i= lcv1, k= tileStartI; i<nRows + boardStartI && k < yDim + tileStartI && flag == true; i++,k++ )
				{
					for(int j = lcv2, l = tileStartJ; j<nCols + boardStartJ && l < xDim + tileStartJ && flag == true; j++, l++)
					{
						
						if(matrix[k][l] == null)	//empty cell
							continue;	
			
						if(matrix[k][l].equals(boardMatrix[i][j]))
						{
							//get ColumnId
							columnId.add(new Integer(boardMatrix[i][j].getId()));
							continue;
						}
						
						flag = false;
						
					}
					
				}
				
				if(flag == true)
				{			
					collectionOfColumnIDs.add(columnId);
					collectionOfColumnIDsSet.add(columnId);
				}
				
				flag = true;
			}
			
		}			
		// return collectionOfColumnIDs;
		return collectionOfColumnIDsSet;
	}



	public static ArrayList<DataObject>  loadGrid(
			HashMap<Tile, Set<ArrayList<Integer>>> mapping, ArrayList<DataObject> topPointerList, int offset) 
	{
		
		ArrayList<DataObject> columnPointerList = new ArrayList<DataObject>();	
		Set<Tile> tileSet = mapping.keySet();		
		
		for(Tile tile : tileSet)
		{
			Set<ArrayList<Integer>> colIdList = mapping.get(tile);	//the list of all configurations of this tile
			int tileId = tile.getId();
			
			for(ArrayList<Integer> colIds : colIdList)	// one such configuration
			{
				DataObject rowLeader = new DataObject();
				columnPointerList.add(rowLeader);
				DataObject prev = new DataObject();
				prev  = rowLeader;
				insertMinion(rowLeader, tileId, topPointerList);
				
				for(Integer colId : colIds)	//each column in a configuration
				{
					DataObject tmp = new DataObject();					
					insertMinion(tmp, colId + offset , topPointerList);					
					prev.right = tmp;
					tmp.left = prev;
					tmp.right = rowLeader;
					rowLeader.left = tmp;
					prev = tmp;
				}
			}
			
		}	
		return columnPointerList;
		
	}

	private static void insertMinion(DataObject dObj, int tileId, ArrayList<DataObject> topPointerList) 
	{
		DataObject columnObject = topPointerList.get(tileId);
		dObj.up = columnObject.up;
		columnObject.up.down = dObj;
		dObj.down = columnObject;
		columnObject.up = dObj;	
		dObj.colData = columnObject;		
		dObj.colData.size += 1;
	}

	public static Integer doExtraCover(int index, DataObject[] dObj, Integer numSolns, DataObject header, int numTiles, int totalNumTiles, ArrayList<ArrayList<DataObject>> columnsCovered, Tile board) 
	{
		
	//	System.out.println("this is attempt number " + (++attempt));
		boolean isPrint = false;
		if(header.right == header)
		{
			//print solution
			for(int lcv=0; lcv<index; ++lcv)
				System.out.print(dObj[lcv].getColData().name + "covers columns" + columnsCovered.get(lcv) +",\t");
		
			System.out.println();
			System.out.println(board);
			
			Table.drawSolution(dObj, board, totalNumTiles, index, columnsCovered);
		//	System.exit(0);
			return ++numSolns;
		}
		
		else
		{
			if(Integer.parseInt(header.left.name) < totalNumTiles)	//denotes I have covered the board with lesser tiles
			{
				System.out.println("WARNING - Less tiles than needed are used");
				for(int lcv=0; lcv<index; ++lcv)
					System.out.print(dObj[lcv].getColData().name + "covers columns" + columnsCovered.get(lcv) +",\t");
				System.out.println();
				
				System.out.println(board);
				Table.drawSolution(dObj, board, totalNumTiles, index, columnsCovered);
			//	System.exit(0);
				return ++numSolns;				
			}
			
			DataObject c = chooseColumn(header, numTiles,totalNumTiles);
			
			if(c==null)
			{
				return numSolns;
			}
				
			
		//	Printing.getNumCoveredByEachTile(header, numTiles);
			
			cover(c,isPrint);
//			Printing.getNumCoveredByEachTile(header, numTiles-1);
			DataObject tmp = c.down;
			while(tmp != c)	//going downwards for each row that fills
			{
				ArrayList<DataObject> coveringList = new ArrayList<DataObject>();
				coveringList.add(c);
				
				dObj[index] = tmp;	//for Printing purpose
								
				DataObject rightTmp = tmp.right;
				while(rightTmp != tmp)
				{
					cover(rightTmp.colData, isPrint);	
					coveringList.add(rightTmp.colData);
					rightTmp = rightTmp.right;					
				}
	//			Printing.getNumCoveredByEachTile(header, numTiles-1);
				columnsCovered.add(index,coveringList);
				
				numSolns = doExtraCover(index+1, dObj, numSolns, header, numTiles-1,totalNumTiles, columnsCovered, board);
				
				DataObject leftTmp = tmp.left;
				while(leftTmp != tmp)
				{
					uncover(leftTmp.colData,isPrint);					
					leftTmp = leftTmp.left;
				}
				
	//			Printing.getNumCoveredByEachTile(header, numTiles-1);
				tmp = tmp.down;
			}
			uncover(c,isPrint);
		}
		//return s;
		return numSolns;
	}	
	
	private static void uncover(DataObject c, boolean isPrint) 
	{
		
		if(isPrint)
			System.out.println("Attempting to uncover "+ c.name);
		
		c.right.left = c;
		c.left.right = c;
		
		DataObject dObj = c.up;
		while(dObj != c)
		{
			DataObject unrowTrash = dObj.left;
			while(unrowTrash != dObj)
			{
				unrowTrash.down.up = unrowTrash;
				unrowTrash.up.down = unrowTrash;
				unrowTrash.colData.size += 1;
				unrowTrash = unrowTrash.left;	//move to the next col
			}
			
			dObj = dObj.up;
		}
		
	}

	private static void cover(DataObject c, boolean isPrint)
	{
		if(isPrint)
			System.out.println("Attempting to cover "+ c.name);
		
		c.right.left = c.left;
		c.left.right = c.right;
		
		DataObject dObj = c.down;
		
		int count = 0;
		
		while(dObj != c)	//for every row that fills c
		{			
			DataObject rowTrash = dObj.right;
			while(rowTrash != dObj)
			{
				
				rowTrash.down.up = rowTrash.up;
				
				rowTrash.up.down = rowTrash.down;
				rowTrash.colData.size -= 1;
				rowTrash = rowTrash.right;	//move to the next col
				
			}
			++ count;
			dObj = dObj.down;
		}
		
//		System.out.println("We covered column" + c.getName() + " " + count + "times");
	}

	private static DataObject chooseColumn(DataObject header, int numTiles, int totalNumTiles) 
	{
		
		if(header.right == header)	//Nothing left ! Actually this code will never be reached !
			return null;
		
		if(Integer.parseInt( header.right.name) >= totalNumTiles)	//All tiles covered, only cells left - meaning nothing more to choose !
			return null;
		
		DataObject tmp = header.right;
		DataObject retVal = null;
		
		int lcv = 0;
		int s = Integer.MAX_VALUE;
		
		while(tmp != header && lcv < numTiles)
		{
			int size = tmp.size;
			if(size < s && size != 0)
			{
				s= tmp.size;
				retVal = tmp;
			}
			tmp = tmp.right;
			++lcv;
		}
		
		if(retVal == null || retVal.size == 0)
		{
//			System.out.println("NOTHING TO CHOOSE");
			return null;
		}
			
//		System.out.println("Choosing tile #:" + retVal.getName());
		
		if(retVal.size == 0)
			return null;
		
		return retVal;
	//	return header.right;
	}		
	
	public static Solution doExtraCover2(int index, DataObject[] dObj, Integer numSolns, DataObject header, int numTiles, int totalNumTiles, ArrayList<ArrayList<DataObject>> columnsCovered, Solution s) 
	{
		
	//	System.out.println("this is attempt number " + (++attempt));
		boolean isPrint = true;
		if(header.right == header)
		{
			//print solution
			for(int lcv=0; lcv<index; ++lcv)
				System.out.print(dObj[lcv].getColData().name + "covers columns" + columnsCovered.get(lcv) +",\t");
		
			System.out.println();
		
			
			s.num++;
			s.isPartial= false;
			return s;
			
		//	return ++numSolns;
		}
		
		/*if(Integer.parseInt(header.right.name) >= numTiles )
			return 0;*/
		
		else
		{
			if(Integer.parseInt(header.left.name) < totalNumTiles)	//denotes I have covered the board with lesser tiles
			{
				System.out.println("WARNING - Less tiles than needed are used");
				for(int lcv=0; lcv<index; ++lcv)
					System.out.print(dObj[lcv].getColData().name + "covers columns" + columnsCovered.get(lcv) +",\t");
				System.out.println();
			//	System.exit(0);
				s.num++;
				s.isPartial= true;
				return s;
				//return ++numSolns;				
			}
			
			DataObject c = chooseColumn(header, numTiles,totalNumTiles);
			
			if(c==null)
			{
			
				s.isPartial = false;
				return s;
				
			//	return numSolns;
			}
				
			
		//	Printing.getNumCoveredByEachTile(header, numTiles);
			
			cover(c,isPrint);
			Printing.getNumCoveredByEachTile(header, numTiles-1);
			DataObject tmp = c.down;
			while(tmp != c)	//going downwards for each row that fills
			{
				ArrayList<DataObject> coveringList = new ArrayList<DataObject>();
				coveringList.add(c);
				
				dObj[index] = tmp;	//for Printing purpose
								
				DataObject rightTmp = tmp.right;
				while(rightTmp != tmp)
				{
					cover(rightTmp.colData,isPrint);	
					coveringList.add(rightTmp.colData);
					rightTmp = rightTmp.right;					
				}
				Printing.getNumCoveredByEachTile(header, numTiles-1);
				columnsCovered.add(index,coveringList);
				
				s = doExtraCover2(index+1, dObj, numSolns, header, numTiles-1,totalNumTiles, columnsCovered, s);
				
				/*if(s.isPartial == true)
				{
					tmp = tmp.down;
					continue;
				}*/
				
				DataObject leftTmp = tmp.left;
				while(leftTmp != tmp)
				{
					uncover(leftTmp.colData,isPrint);			//uncovers this row for the chosen column		
					leftTmp = leftTmp.left;
				}
				
				Printing.getNumCoveredByEachTile(header, numTiles-1);
				tmp = tmp.down;
			}
			uncover(c,isPrint);	//uncovers this column-choice !
		}
		return s;
		//return numSolns;
	}
	
	
}
