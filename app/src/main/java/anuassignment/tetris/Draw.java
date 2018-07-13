package anuassignment.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static anuassignment.tetris.TetrisView.NUMBER_OF_COL;
import static anuassignment.tetris.TetrisView.NUMBER_OF_ROW;
import static anuassignment.tetris.TetrisView.board_x;
import static anuassignment.tetris.TetrisView.board_y;
import static anuassignment.tetris.TetrisView.fillPaint;
import static anuassignment.tetris.TetrisView.queue_x;
import static anuassignment.tetris.TetrisView.queue_y;
import static anuassignment.tetris.TetrisView.squareHeight;
import static anuassignment.tetris.TetrisView.squareStrokeWidth;
import static anuassignment.tetris.TetrisView.squareWidth;
import static anuassignment.tetris.TetrisView.strokePaint;

/**
 * Created by chaahatjain on 11/7/18.
 */

public class Draw {
    /**
     * Used to draw the matrix for the game
     *
     * @param canvas
     * @return
     */
    public static Canvas drawMatrix(Canvas canvas) {

        Rect rect = new Rect(board_x, board_y, board_x + squareWidth * NUMBER_OF_COL, board_y + squareHeight * NUMBER_OF_ROW);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.WHITE);
        canvas.drawRect(rect, fillPaint);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.DKGRAY); // Need to make a lighter shade
        strokePaint.setStrokeWidth(20);
        canvas.drawRect(rect, strokePaint);
        fillPaint.setColor(Color.BLACK);
        fillPaint.setStrokeWidth(squareStrokeWidth);
        for (int i = 1; i <= NUMBER_OF_ROW; i++) {
            canvas.drawLine(board_x, board_y + i * squareHeight, board_x + squareWidth * NUMBER_OF_COL, board_y + i * squareHeight, fillPaint);
        }
        for (int i = 1; i <= NUMBER_OF_COL; i++) {
            canvas.drawLine(board_x + i * squareWidth, board_y, board_x + i * squareWidth, board_y + NUMBER_OF_ROW * squareHeight, fillPaint);
        }
        return canvas;
    }

    /**
     * Draw the place where the next few pieces will be added
     *
     * @param canvas
     * @return
     */
    public static Canvas drawQueue(Canvas canvas) {
        int startX = queue_x;
        int startY = queue_y;
        int endX = startX + 4 * (squareWidth + squareStrokeWidth);
        int endY = startY + 15 * squareHeight;
        fillPaint.setColor(Color.WHITE);
        Rect rect = new Rect(startX, startY, endX, endY);
        canvas.drawRect(rect, fillPaint);
        canvas.drawRect(rect, strokePaint);
        return canvas;
    }

    /**
     * Draws the nextPiece holder. If required can make it draw the next piece as well
     *
     * @param canvas
     * @return
     */
    public static Canvas drawNextPieceHolder(Canvas canvas) {
        int center_x = board_x + (NUMBER_OF_COL + 2) * squareWidth + 20 + 10 * squareStrokeWidth;
        int center_y = board_y + 2 * squareHeight;
        int radius = 2 * squareHeight;
        canvas.drawCircle(center_x, center_y, radius, fillPaint);
        canvas.drawCircle(center_x, center_y, radius, strokePaint);
        return canvas;
    }

    /**
     * Draw the Piece Holder. If required, can make it draw the piece as well.
     * @param canvas
     * @return
     */
    public static Canvas drawHoldPieceHolder(Canvas canvas) {
        int center_x = board_x - 20 - 2 * squareWidth - 10 * squareStrokeWidth;
        int center_y = board_y + 2 * squareHeight;
        int radius = 2 * squareHeight;
        canvas.drawCircle(center_x, center_y, radius, fillPaint);
        canvas.drawCircle(center_x, center_y, radius, strokePaint);
        return canvas;
    }

    /**
     * Used to fill the squares for the entire board
     * @param canvas
     * @param board
     * @param typeBoard
     * @return
     */
    public static Canvas fillSquares(Canvas canvas, int[][] board, char[][] typeBoard) {
        Paint paint = new Paint();
        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(squareStrokeWidth);
        for (int i = 0; i < NUMBER_OF_ROW; i++) {
            for (int j = 0; j < NUMBER_OF_COL; j++) {
                if (board[i][j] == 1) {
                    int x = board_x + j*squareWidth;
                    int y = board_y + i*squareHeight;
                    int endX = x + squareWidth;
                    int endY = y + squareHeight;
                    Rect rect = new Rect(x, y, endX, endY);
                    char type = typeBoard[i][j];
                    paint.setColor(Tetrimino.chooseColor(type));
                    canvas.drawRect(rect,paint);
                    canvas.drawRect(rect,strokePaint);
                }
            }
        }
        return canvas;
    }
}
