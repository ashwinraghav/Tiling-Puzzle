/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zegame;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author ashwinraghav
 */
public class MyCanvas extends JComponent {

    int board[][], height, width;
    Color[] colors;

    public void paint(Graphics g) {
        int slack = 15;
        int border_thickness = 3;
        int domino_height = (int) ((height - (4 * slack)) / board.length);
        int domino_width = (int) ((width - (4 * slack)) / board[0].length);

        Graphics2D g2 = (Graphics2D) g;
        System.out.println(this.board.length);
        System.out.println(this.board[0].length);

        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                if (board[i][j] != -1) {
                    //meaning something exists
                    g2.setPaint(colors[board[i][j] % colors.length]);
                    g2.fill(new Rectangle2D.Double(slack + (j * domino_width), slack + (i * domino_height), domino_width, domino_height));

                    if (board[i][j + 1] == -1) {//right edge is the border
                        g2.setPaint(Color.BLACK);
                        g2.fill(new Rectangle2D.Double(slack + ((j + 1) * domino_width), slack + ((i) * domino_height), border_thickness, domino_height));
                    }
                    if (board[i][j - 1] == -1) {//left edge is the border
                        g2.setPaint(Color.BLACK);
                        g2.fill(new Rectangle2D.Double(slack + ((j) * domino_width), slack + ((i) * domino_height), border_thickness, domino_height));
                    }
                    if (board[i + 1][j] == -1) {//bottom edge is the border
                        g2.setPaint(Color.BLACK);
                        g2.fill(new Rectangle2D.Double(slack + ((j) * domino_width), slack + ((i + 1) * domino_height), domino_width, border_thickness));
                    }
                    if (board[i - 1][j] == -1) {//top edge is the border
                        g2.setPaint(Color.BLACK);
                        g2.fill(new Rectangle2D.Double(slack + ((j) * domino_width), slack + ((i) * domino_height), domino_width, border_thickness));
                    }
                }
            }
        }
    }

    MyCanvas(int board[][], int width, int height) {
        this.height = height;
        this.width = width;
        setBoundaries(board);
        colors = new Color[12];
        colors[0] = Color.YELLOW;
        colors[1] = Color.BLUE;
        colors[2] = Color.CYAN;
        colors[3] = Color.DARK_GRAY;
        colors[4] = Color.GRAY;
        colors[5] = Color.GREEN;
        colors[6] = Color.LIGHT_GRAY;
        colors[7] = Color.MAGENTA;
        colors[8] = Color.ORANGE;
        colors[9] = Color.PINK;
        colors[10] = Color.RED;
        colors[11] = Color.YELLOW;

        //Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GREEN, Color.GREEN, Color.GREEN, Color.MAGENTA}

    }

    private void setBoundaries(int board[][]) {
        this.board = new int[board.length + 2][board[0].length + 2];

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if ((j == 0) || (i == 0) || (j == this.board[0].length - 1) || (i == this.board.length - 1)) {
                    this.board[i][j] = -1;
                } else {
                    this.board[i][j] = board[i - 1][j - 1];
                }
            }
        }

    }
}
