package anuassignment.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by chaahatjain on 10/7/18.
 */

public class Piece {
    private Square[] squares;
    private int positionX;
    private int positionY;
    char type;

    final static int PIECE_AREA = 4;
    final static String PIECE_TYPES = "LIOJSZT";

    public Piece(int positionX, int positionY, char type) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        squares = getAllSquares(type);
    }

    public Piece(Square[] squares, int positionX, int positionY, char type) {
        this.squares = squares;
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
    }

    private Square[] getAllSquares(char type) {
        Square[] array = new Square[PIECE_AREA];
        array[0] = new Square(0, 0);
        switch (type) {
            case 'I':
                array[1] = new Square(1, 0);
                array[2] = new Square(2, 0);
                array[3] = new Square(4, 0);
                break;
            case 'J':
                array[1] = new Square(-1, 0);
                array[2] = new Square(-2, 0);
                array[3] = new Square(0, 1);
                break;
            case 'L':
                array[1] = new Square(0, 1);
                array[2] = new Square(1, 0);
                array[3] = new Square(2, 0);
                break;
            case 'O':
                array[1] = new Square(1, 0);
                array[2] = new Square(0, 1);
                array[3] = new Square(1, 1);
                break;
            case 'S':
                array[1] = new Square(1, 0);
                array[2] = new Square(0, -1);
                array[3] = new Square(-1, -1);
                break;
            case 'Z':
                array[1] = new Square(-1, 0);
                array[2] = new Square(0, -1);
                array[3] = new Square(1, -1);
                break;
            case 'T':
                array[1] = new Square(1, 0);
                array[2] = new Square(-1, 0);
                array[3] = new Square(0, -1);
                break;
        }
        return array;
    }

    /**
     * Algorithm to rotate a piece clock-wise
     *
     * @return
     */
    public Piece rotatePiece() {
        Square[] array = new Square[4];
        for (int i = 0; i < 4; i++) {
            int x = squares[i].getY();
            int y = -1 * squares[i].getX();
            array[i] = new Square(x, y);
        }
        Piece piece = new Piece(array, this.positionX, this.positionY, this.type);
        return piece;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Square[] getSquares() {
        return squares;
    }

    /**
     * Checks whether there is a piece beneath any of the squares
     *
     * @param board
     * @return
     */
     boolean fixPiece(int[][] board) {
        for (int i = 0; i < squares.length; i++) {
            Square square = squares[i];
            int squareX = square.getX() + positionX; // column number
            int squareY = square.getY() + positionY; // row number

            if (board[squareY + 1][squareX] == 1) {
                return true;
            }
        }
        return false;
    }

     static Piece generatePiece() {
        Random random = new Random();
        int index = random.nextInt(PIECE_TYPES.length());
        char type = PIECE_TYPES.charAt(index);

        Piece currentPiece = new Piece(5, 0, type);
        return currentPiece;
    }

    /**
     * Paints all squares of the piece onto the canvas
     * TO_DO : Change it so that for different types of Blocks it takes in different color's
     * @param canvas
     * @param paint
     * @param squareWidth
     * @param squareHeight
     * @return
     */
    Canvas drawPiece(Canvas canvas,Paint paint, int squareWidth, int squareHeight) {
         for (int i = 0; i < squares.length; i++) {
             Rect rect = new Rect(positionX, positionY, positionX + squareWidth, positionY + squareHeight);
             canvas.drawRect(rect, paint);
         }
         return canvas;
    }
}
