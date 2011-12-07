/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zegame;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author ashwinraghav
 */
public class Table {

    public static void main(String[] a) {
        //int board[][] = {{1, 2, 3, 4, -1}, {1, 2, 4, 3, 5}, {3, 2, 1, -1, -1}};
        int board[][] = {{1,-1,2,3,3}, 
                         {1,1,2,2,2}};
        int top_left_x = 0;
        int top_left_y = 0;
        int bottom_right_x = 500;
        int bottom_right_y = 500;

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setBounds(top_left_x, top_left_y, bottom_right_x, bottom_right_y);

        MyCanvas canvas = new MyCanvas(board, bottom_right_x - top_left_x, bottom_right_y - top_left_y);
        window.getContentPane().add(canvas);
        window.setVisible(true);
    }
}
