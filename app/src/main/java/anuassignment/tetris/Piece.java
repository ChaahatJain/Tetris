package anuassignment.tetris;
import android.graphics.Color;
import java.util.Random;

/**
 * Created by chaahatjain on 10/7/18.
 */

public class Piece {
    private Square[] squares;
    private int positionX;
    private int positionY;
    private int color;
    char type;

    final static int PIECE_AREA = 4;
    final static String PIECE_TYPES = "LIOJSZT";

    public Piece(int positionX, int positionY, char type) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        squares = getAllSquares(type);
        color = chooseColor(type);
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
                array[3] = new Square(3, 0);
                break;
            case 'J':
                array[1] = new Square(-1, 0);
                array[2] = new Square(-2, 0);
                array[3] = new Square(0, -1);
                break;
            case 'L':
                array[1] = new Square(0, -1);
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
                array[2] = new Square(0, 1);
                array[3] = new Square(-1, 1);
                break;
            case 'Z':
                array[1] = new Square(-1, 0);
                array[2] = new Square(0, 1);
                array[3] = new Square(1, 1);
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
    public Piece rotatePieceClockWise() {
        Square[] array = new Square[4];
        for (int i = 0; i < 4; i++) {
            int x = -1 * squares[i].getY();
            int y = squares[i].getX();
            array[i] = new Square(x, y);
        }
        Piece piece = new Piece(array, this.positionX, this.positionY, this.type);
        return piece;
    }

    /**
     * Algorithm to return the piece anticlockwise
     * @return
     */
    public Piece rotatePieceAntiClockWise() {
        Square[] array = new Square[4];
        for (int i = 0; i < 4; i++) {
            int x = squares[i].getY();
            int y = -1 * squares[i].getX();
            array[i] = new Square(x, y);
        }
        Piece piece = new Piece(array, this.positionX, this.positionY, this.type);
        return piece;
    }

    // Need a drawPiece and a drawSquare Method

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



     static Piece generatePiece() {
        Random random = new Random();
        int index = random.nextInt(PIECE_TYPES.length());
        char type = PIECE_TYPES.charAt(index);

        Piece currentPiece = new Piece(5, 0, type);
        return currentPiece;
    }



    /**
     * Decide on a color for each and every piece
     * @param type
     * @return
     */
    private int chooseColor(char type) {
        switch (type) {
            case 'O' : return Color.YELLOW;
            case 'I' : return Color.argb(255, 51, 204, 255);
            case 'J' : return Color.argb(255, 255, 153, 0);
            case 'L' : return Color.argb(255, 51, 51, 255);
            case 'T' : return Color.argb(255, 0, 0, 153);
            case 'S' : return Color.argb(255, 0, 230, 0);
            case 'Z' : return Color.argb(255, 255, 0, 0);
            default: return Color.WHITE;
        }
    }
}
