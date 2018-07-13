package anuassignment.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static anuassignment.tetris.Draw.drawHoldPieceHolder;
import static anuassignment.tetris.Draw.drawMatrix;
import static anuassignment.tetris.Draw.drawNextPieceHolder;
import static anuassignment.tetris.Draw.drawQueue;


/**
 * Created by chaahatjain on 10/7/18.
 * Purpose of class is to show the view of the game and do basic things like update and draw
 */

public class TetrisView extends SurfaceView implements Runnable {

    Context context;
    int screenWidth;
    int screenHeight;

    volatile boolean playing; // for pausing and resuming the game

    // artwork
    private SurfaceHolder ourHolder;
    Canvas canvas;
    public static Paint fillPaint;
    public static Paint strokePaint;

    Thread gameThread = null;
    private long time = 17;

    // currentPiece controlled by the player

    Tetrimino currentTetrimino;
    Tetrimino[] nextTetriminos;
    int tetriminosInQueue;
    boolean gameOver;

    // playing Board
    final static int NUMBER_OF_COL = 10;
    final static int NUMBER_OF_ROW = 20;
    final static int squareWidth = 65;
    final static int squareHeight = 65;
    final static int squareStrokeWidth = 5;
    static int board_x, board_y;
    static int queue_x, queue_y;

    int[][] board = new int[NUMBER_OF_ROW + 1][NUMBER_OF_COL]; // board is defined row-wise; i.e for every row you need to check 10 columns
    char[][] typeBoard = new char[NUMBER_OF_ROW][NUMBER_OF_COL];
    // 1 extra row for the entire bottom no need to draw this row.

    public TetrisView(Context context, int x, int y) {
        super(context);
        this.context = context;
        screenWidth = x;
        screenHeight = y;

        board_x = screenWidth / 3;
        board_y = squareHeight;

        queue_x = board_x + NUMBER_OF_COL * squareWidth + 30 + 15 * squareStrokeWidth;
        queue_y = board_y + 5 * squareHeight;

        ourHolder = getHolder();
        fillPaint = new Paint();
        strokePaint = new Paint();

        // Get the queue
        nextTetriminos = new Tetrimino[6];
        tetriminosInQueue = nextTetriminos.length;

        startGame();
    }

    // initialise stuff for the game
    private void startGame() {
        // get a random tetrimino controlled by the player
        currentTetrimino = Tetrimino.generateTetrimino();

        for (int i = 0; i < 20; i++) {
            board[i] = new int[NUMBER_OF_COL];
            typeBoard[i] = new char[NUMBER_OF_COL];
        }
        // initialising the last row
        for (int i = 0; i < 10; i++) {
            board[20][i] = 1;
        }
        gameOver = false;
        initialiseQueue();
    }

    /**
     * Used to initialise the queue for the game
     */
    private void initialiseQueue() {
        for (int i = 0; i < nextTetriminos.length; i++) {
            nextTetriminos[i] = Tetrimino.generateTetrimino();
        }
        tetriminosInQueue = nextTetriminos.length;
    }

    @Override
    public void run() {
        // when in playing state - update, draw, control the thread
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {

        if (checkTetrimino()) { // check whether piece is going to be fixed or not
            updateMatrix(); // if yes, update the board

            int numberOfRowsCleared = clearLines(); // used for scoring

            currentTetrimino = nextTetriminos[nextTetriminos.length - tetriminosInQueue]; // Get the next piece out
            tetriminosInQueue--;

            if (tetriminosInQueue == 0) initialiseQueue();
        } else {
            currentTetrimino.setCenterRow(currentTetrimino.getCenterRow() + 1);
        }
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            // lock the canvas
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.GRAY);
            canvas = drawMatrix(canvas); // draw an empty matrix
            canvas = Draw.fillSquares(canvas, board, typeBoard); // Used to color the squares appropriately
            drawCurrentTetrimino();
            canvas = drawQueue(canvas); // draw an empty queue
            drawQueueTetrimino();
            canvas = drawNextPieceHolder(canvas); // draw an empty nextPieceHolder
            drawNextTetrimino();
            drawGhostPiece();
            canvas = drawHoldPieceHolder(canvas); // draw an empty pieceHolder
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {

        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        // target specifies which objects run method to use
        // this means use the run method in this class
        gameThread.start();
    }

    /**
     * Used to check whether there is a mino below the given tetrimino or not
     *
     * @return
     */
    private boolean checkTetrimino() {
        int indexX = currentTetrimino.getCenterCol();
        int indexY = currentTetrimino.getCenterRow();
        Tetrimino.Tuple[] squares = currentTetrimino.getSquares();
        for (Tetrimino.Tuple tuple : squares) {
            int col = tuple.x + indexX;
            int row = tuple.y + indexY;
            if (board[row + 1][col] == 1) return true;
        }

        return false;
    }

    /**
     * Update the details of a matrix when a tetrimino falls on it
     */
    private void updateMatrix() {
        int indexX = currentTetrimino.getCenterCol();
        int indexY = currentTetrimino.getCenterRow();
        Tetrimino.Tuple[] squares = currentTetrimino.getSquares();
        for (Tetrimino.Tuple square : squares) {
            int x = square.x + indexX;
            int y = square.y + indexY;
            board[y][x] = 1;
            typeBoard[y][x] = currentTetrimino.getType();
        }
    }

    /**
     * Check whether any line has been filled or not. If it has then remove the line
     *
     * @return the number of rows that have been removed like this
     */
    private int clearLines() {
        int numberRemoved = 0;
        for (int i = 0; i < NUMBER_OF_ROW; i++) {
            if (lineClear(i - numberRemoved)) {
                clearLine(i - numberRemoved);
                numberRemoved++;
            }
        }
        return numberRemoved;
    }

    /**
     * Check whther given row is full or not
     *
     * @param y
     * @return
     */
    private boolean lineClear(int y) {
        for (int i = 0; i < NUMBER_OF_COL; i++) {
            if (board[y][i] != 1) return false;
        }
        return true;
    }

    /**
     * clear a line from both matrices
     *
     * @param y : Row index to be removed
     */
    private void clearLine(int y) {
        int subtract = 0;
        for (int i = NUMBER_OF_ROW; i > 0; i--) {
            if (i == y) {
                subtract = 1;
            }
            board[i] = board[i - subtract];
            typeBoard[i] = typeBoard[i - subtract];
        }

        board[0] = new int[NUMBER_OF_COL];
        typeBoard[0] = new char[NUMBER_OF_COL];
    }

    /**
     * Draw the current piece onto the canvas
     */
    private void drawCurrentTetrimino() {
        int col = currentTetrimino.getCenterCol();
        int row = currentTetrimino.getCenterRow();

        int startX = board_x + col * squareWidth;
        int startY = board_y + row * squareHeight;

        canvas = currentTetrimino.drawTetrimino(canvas, startX, startY);

    }

    /**
     * Draw the tetrimino's in the queue except for the next one
     */
    private void drawQueueTetrimino() {
        int pieceNumber = 0;
        for (int i = nextTetriminos.length - tetriminosInQueue + 1; i < nextTetriminos.length; i++) {
            Tetrimino tetrimino = nextTetriminos[i];
            int x = queue_x + 2 * squareWidth;
            int y = queue_y + (3 * pieceNumber + 1) * squareHeight + 30;
            canvas = tetrimino.drawTetrimino(canvas, x, y);
            pieceNumber++;
        }
    }

    private void drawNextTetrimino() {
        int center_x = queue_x + 2 * squareWidth - 10 + 5 * squareStrokeWidth;
        int center_y = queue_y - 3 * squareHeight;
        Tetrimino next = nextTetriminos[nextTetriminos.length - tetriminosInQueue];
        canvas = next.drawTetrimino(canvas, center_x, center_y);

    }

    private void drawGhostPiece() {
        Tetrimino.Tuple[] tuples = currentTetrimino.getSquares();
        int col = currentTetrimino.getCenterCol();
        int row = currentTetrimino.getCenterRow();

        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);


        int leastRow = NUMBER_OF_ROW + 2;
        Tetrimino.Tuple relative = null;

        for (Tetrimino.Tuple tuple : tuples) {
            int x = col + tuple.x;
            int y = row + tuple.y;
            int lowestRow = getLowestAvailableRow(x, y);
            if (leastRow >= lowestRow) {
                leastRow = lowestRow;
                relative = tuple;
            }
        }

        for (Tetrimino.Tuple tuple : tuples) {
            int x = col + tuple.x;
            int y = leastRow + (tuple.y - relative.y);

            int left = board_x + x * squareWidth;
            int top = board_y + y * squareHeight;
            int right = left + squareWidth;
            int bottom = top + squareHeight;
            Rect rect = new Rect(left,top,right,bottom);

            canvas.drawRect(rect,paint);


        }


    }

    private int getLowestAvailableRow(int col, int row) {
        int max = 0;
        for (int i = row + 1; i <= NUMBER_OF_ROW; i++) {
            if (board[i][col] == 1) {
                return i - 1;
            }
        }
        return max;
    }


}
