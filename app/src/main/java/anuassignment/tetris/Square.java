package anuassignment.tetris;

/**
 * Created by chaahatjain on 10/7/18.
 */

class Square {
    private int x;
    private int y;
    public final int SQUARE_HEIGHT = 65;
    public final int SQUARE_WIDTH = 65;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
