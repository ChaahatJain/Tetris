package anuassignment.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


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
    Paint paint;

    Thread gameThread = null;
    private long time = 17;

    // currentPiece controlled by the player
    Piece currentPiece;
    boolean gameOver;

    // playing Board
    final static int NUMBER_OF_COL = 10;
    final static int NUMBER_OF_ROW = 20;
    final static int squareWidth = 5;
    final static int squareHeight = 5;

    int[][] board = new int[NUMBER_OF_ROW + 1][NUMBER_OF_COL]; // board is defined row-wise; i.e for every row you need to check 10 columns
    // 1 extra row for the entire bottom no need to draw this row.

    public TetrisView(Context context, int x, int y) {
        super(context);
        System.out.println("Initialisation started");
        this.context = context;
        screenWidth = x;
        screenHeight = y;

        ourHolder = getHolder();
        paint = new Paint();
        startGame();
    }

    // initialise stuff for the game
    private void startGame() {
        // get a random piece controlled by the player
        currentPiece = Piece.generatePiece();

        for (int i = 0; i < 20; i++) {
            board[i] = new int[10];
        }
        // initialising the last row
        for (int i = 0; i < 10; i++) {
            board[20][i] = 1;
        }
        gameOver = false;

        System.out.println("Initialisation Over");
    }

    @Override
    public void run() {
        // when in playing state - update, draw, control the thread
        System.out.println("In run");
        while (playing) {
            System.out.println("Entering update");
            update();
            System.out.println("Exiting update and entering draw");

            draw();
            control();
        }
    }

    private void update() {

        // if touching ground, or any other block;
        // add the entire piece to the board
        if (currentPiece.fixPiece(board)) {

            // attach the currentPiece to the board
            // then check whether the row is entirely full or not and update accordingly
            attachPieceToBoard(currentPiece, board);

            if (!gameOver) {
                // generate a new Piece
                currentPiece = Piece.generatePiece();
            }

        }
        // update the yCoord of this piece
        else {

            int y = currentPiece.getPositionY();
            currentPiece.setPositionY(++y);
        }
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            // lock the canvas
            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 0, 0, 0)); // make the entire previous canvas black
            paint.setColor(Color.argb(255, 255, 255, 255)); // paint is white
            if (gameOver) canvas.drawText("Game is Over", screenWidth / 2, screenHeight / 2, paint);
            else {
                for (int i = 0; i < NUMBER_OF_ROW; i++) {
                    canvas.drawLine(0,i * squareHeight, NUMBER_OF_COL*squareWidth, i * squareHeight, paint);
                    for (int j = 0; j < NUMBER_OF_COL; j++) {
                        canvas.drawLine(0,j * squareWidth, NUMBER_OF_ROW*squareHeight, j * squareWidth, paint);
                        if (board[i][j] == 1) {
                            Rect rect = new Rect(i, j, i + squareWidth, j + squareHeight); // size of the square to be made. Currently superSuspicious.
                            canvas.drawRect(rect, paint);
                        }
                    }
                }
                // draw the currentPiece
                System.out.println("Checking drawPiece");
                canvas = currentPiece.drawPiece(canvas, paint, squareWidth, squareHeight);
                System.out.println("Culprit");
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(time);
        }
        catch (InterruptedException e) {

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
     * attach the currentPiece to the board if it cannot move
     *
     * @param piece
     * @param board
     */
    private void attachPieceToBoard(Piece piece, int[][] board) {
        Square[] squares = piece.getSquares();
        for (int i = 0; i < squares.length; i++) {
            Square square = squares[i];
            int squareX = square.getX() + piece.getPositionX();
            int squareY = square.getY() + piece.getPositionY();
            if (board[squareY][squareX] == 1) gameOver = true;
            else {
                board[squareY][squareX] = 1;
                if (fullRow(board, squareY)) {
                    board[squareY] = new int[NUMBER_OF_COL];
                }
            }
        }
    }

    /**
     * checks whether the entire row is full or not
     * @param board
     * @param rowNumber
     * @return
     */
    private boolean fullRow(int[][] board, int rowNumber) {

        for (int i = 0; i < NUMBER_OF_COL; i++) {
            if (board[rowNumber][i] != 1) {
                return false;
            }
        }
        return true;
    }

}
