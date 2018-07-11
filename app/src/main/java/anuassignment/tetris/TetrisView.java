package anuassignment.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    boolean gameOver;

    // playing Board
    final static int NUMBER_OF_COL = 10;
    final static int NUMBER_OF_ROW = 20;
    final static int squareWidth = 65;
    final static int squareHeight = 65;
    final static int squareStrokeWidth = 5;
    static int board_x, board_y;

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

        ourHolder = getHolder();
        fillPaint = new Paint();
        strokePaint = new Paint();
        startGame();
    }

    // initialise stuff for the game
    private void startGame() {
        // get a random piece controlled by the player
        currentPiece = Piece.generatePiece();

        for (int i = 0; i < 20; i++) {
            board[i] = new int[NUMBER_OF_COL];
            typeBoard[i] = new char[NUMBER_OF_COL] ;
        }
        // initialising the last row
        for (int i = 0; i < 10; i++) {
            board[20][i] = 1;
        }
        gameOver = false;

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
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            // lock the canvas
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.GRAY);
            canvas = drawMatrix(canvas);
            canvas = drawQueue(canvas);
            canvas = drawNextPieceHolder(canvas);
            canvas = drawHoldPieceHolder(canvas);
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(time);
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




}
