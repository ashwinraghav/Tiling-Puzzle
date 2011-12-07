/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zegame;

import java.util.ArrayList;

import javax.swing.JFrame;

import main.Driver;
import puzzle.Domino;
import puzzle.Tile;
import algorithm.dancinglinks.DataObject;


/**
 *
 * @author ashwinraghav
 */
public class Table {

    public static void main(String[] a) 
    {
        
        String inputFile = new String ("/home/avinash/Downloads/puzzles/myTest3.txt");
        boolean isFlip = true;
		Driver.start(inputFile, isFlip);
        
    }
    
    public static void drawSolution(DataObject[] matchList, Tile board, int numTiles, int lastIndex, ArrayList<ArrayList<DataObject>> columnsCovered)
    {
    	
    	int[][] drawingBoardMatrix = new int[(board.getMatrix()).length][(board.getMatrix())[0].length];
    	
    	
    	
    	int counter = 0;
    	
    	int size = drawingBoardMatrix[0].length * drawingBoardMatrix.length;
    	
    	int iMatrix[] = new int[size];
    	int jMatrix[] = new int[size];
    	
    	int xDim = drawingBoardMatrix.length;
    	int yDim = drawingBoardMatrix[0].length;
    	
    	Domino[][]boardMatrix = board.getMatrix();
    	
    	for(int lcvI=0; lcvI<xDim; ++lcvI)
    	{
    		for(int lcvJ=0; lcvJ<yDim; ++lcvJ)
    		{
    			drawingBoardMatrix[lcvI][lcvJ] = -1;
    		}
    	}
    	
    	
    	for(int lcvI=0; lcvI<xDim; ++lcvI)
    	{
    		for(int lcvJ=0; lcvJ<yDim; ++lcvJ)
    		{
    			
    			if(boardMatrix[lcvI][lcvJ] == null)
    				continue;
    			
    			else
    			{
    				iMatrix[counter] = lcvI;
    				jMatrix[counter] = lcvJ;
    				++counter;
    			}
    			
    		}
    	}
    	
    	for(int lcv=0; lcv<lastIndex; ++lcv)
    	{
    		int tileId = Integer.parseInt(matchList[lcv].getColData().getName());
    		ArrayList<DataObject> cellsCovered = columnsCovered.get(lcv);
    		
    		for(int lcv2=1; lcv2<cellsCovered.size(); ++lcv2)
    		{
    			int cellId = Integer.parseInt(cellsCovered.get(lcv2).getName()) - numTiles;
    			drawingBoardMatrix[iMatrix[cellId]][jMatrix[cellId]] = tileId;
    		}
    		
    	}
    	
    	int top_left_x = 0;
        int top_left_y = 0;
        int bottom_right_x = 500;
        int bottom_right_y = 500;

        
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setBounds(top_left_x, top_left_y, bottom_right_x, bottom_right_y);

        
        MyCanvas canvas = new MyCanvas(drawingBoardMatrix, bottom_right_x - top_left_x, bottom_right_y - top_left_y);
        window.getContentPane().add(canvas);
        window.setVisible(true);
    	
    }
}

