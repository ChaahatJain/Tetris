package anuassignment.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    Piece currentPiece;
    Piece heldPiece;
    Piece[] queue;
    boolean gameOver;

    int queuePieces;
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

        queue_x = board_x + NUMBER_OF_COL * squareWidth + 20 + 10 * squareStrokeWidth;
        queue_y = board_y + 5 * squareHeight;

        ourHolder = getHolder();
        fillPaint = new Paint();
        strokePaint = new Paint();
        queue = new Piece[7];
        queuePieces = 7;
        startGame();
    }

    // initialise stuff for the game
    private void startGame() {
        // get a random piece controlled by the player
        currentPiece = Piece.generatePiece();

        for (int i = 0; i < 20; i++) {
            board[i] = new int[NUMBER_OF_COL];
            typeBoard[i] = new char[NUMBER_OF_COL];
        }
        // initialising the last row
        for (int i = 0; i < 10; i++) {
            board[20][i] = 1;
        }
        gameOver = false;
        heldPiece = null;
        initialiseQueue();
    }

    /**
     * Used to initialise the queue for the game
     */
    private void initialiseQueue() {
        for (int i = 0; i < queue.length; i++) {
            queue[i] = Piece.generatePiece();
        }
        queuePieces = queue.length;
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

        if (checkPiece()) { // check whether piece is going to be fixed or not
            updateBoard(); // if yes, update the board

            int numberOfRowsCleared = clearRows(); // used for scoring

            currentPiece = queue[queue.length - queuePieces]; // Get the next piece out
            queuePieces--;

            if (queuePieces == 0) initialiseQueue();
        } else {
            currentPiece.setPositionY(currentPiece.getPositionY() + 1);
        }
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            // lock the canvas
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.GRAY);
            canvas = drawMatrix(canvas); // draw an empty matrix
            canvas = Draw.fillSquares(canvas, board, typeBoard); // Used to color the squares appropriately
            drawCurrentPiece();
            canvas = drawQueue(canvas); // draw an empty queue
            drawQueuePieces(queue);
            canvas = drawNextPieceHolder(canvas); // draw an empty nextPieceHolder
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
     * Used to check whether there is a square below the given piece or not
     *
     * @return
     */
    private boolean checkPiece() {
        int indexX = currentPiece.getPositionX();
        int indexY = currentPiece.getPositionY();
        Square[] squares = currentPiece.getSquares();
        for (int i = 0; i < 4; i++) {
            Square square = squares[i];
            int x = square.getX() + indexX;
            int y = square.getY() + indexY;
            if (board[y + 1][x] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update the details of a board when a piece falls on it
     */
    private void updateBoard() {
        int indexX = currentPiece.getPositionX();
        int indexY = currentPiece.getPositionY();
        Square[] squares = currentPiece.getSquares();
        for (Square square : squares) {
            int x = square.getX() + indexX;
            int y = square.getY() + indexY;
            board[y][x] = 1;
            typeBoard[y][x] = currentPiece.getType();
        }

    }

    /**
     * Check whether any row has been filled or not. If it has then remove the row
     *
     * @return the number of rows that have been removed like this
     */
    private int clearRows() {
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
     * clear a line from both grids
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
    private void drawCurrentPiece() {
        Square[] squares = currentPiece.getSquares();
        int positionX = currentPiece.getPositionX();
        int positionY = currentPiece.getPositionY();
        char type = currentPiece.getType();
        Paint paint = new Paint();
        paint.setColor(Piece.chooseColor(type));
        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(squareStrokeWidth);
        for (int i = 0; i < squares.length; i++) {
            int x = board_x + (positionX + squares[i].getX()) * squareWidth;
            int y = board_y + (positionY + squares[i].getY()) * squareHeight;
            int endX = x + squareWidth;
            int endY = y + squareHeight;
            Rect rect = new Rect(x, y, endX, endY);
            canvas.drawRect(rect, paint);
            canvas.drawRect(rect, strokePaint);
        }

    }

    private void drawQueuePieces(Piece[] queue) {
        int pieceNumber = 0;
        for (int i = queue.length - queuePieces + 1; i < queue.length; i++) {
            Piece piece = queue[i];
            int x = queue_x + squareWidth;
            int y = queue_y + (pieceNumber + 4) * squareHeight;
            canvas = piece.drawPiece(canvas, x, y);
            pieceNumber++;
        }
    }


}
