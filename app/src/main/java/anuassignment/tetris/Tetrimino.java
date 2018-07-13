package anuassignment.tetris;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by chaahatjain on 13/7/18.
 */

public class Tetrimino {
     class Tuple {
        int x, y;
        final int SQUARE_WIDTH = 65;
        final int SQUARE_HEIGHT = 65;
         Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int centerRow,centerCol;
    private char type,orientation;
    private Tuple[] squares;
    private Tuple[] rotationPoints;

    private final static String ORIENTATIONS = "NWSE";
    private final static String TYPES = "IJLSZOT";

    public Tetrimino(int centerCol, int centerRow, char type, char orientation) {
        this.centerRow = centerRow;
        this.centerCol = centerCol;
        this.type = type;
        this.orientation = orientation;
        generateSquares(type,orientation);
    }

    private void generateSquares(char type, char orientation) {
        switch ("" + type + orientation) {
            case "IN" :

                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(2,0),new Tuple(-1,0), new Tuple(2,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(2,0)};

                break;
            case "IE" :
                rotationPoints = new Tuple[] {new Tuple(-1,0),new Tuple(0,0), new Tuple(0,0), new Tuple(0,-1), new Tuple(0,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(0,2)};
                break;

            case "IW":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,2), new Tuple(0,-1)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(0,2)};
                break;

            case "IS":
                rotationPoints = new Tuple[] {new Tuple(0,-1), new Tuple(2,-1), new Tuple(-1,-1), new Tuple(2,0), new Tuple(-1,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(2,0)};
                break;

            case "TN":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(-1,0), new Tuple(1,0)};
                break;

            case "TE":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(1,1), new Tuple(0,-2), new Tuple(1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(1,0)};
                break;

            case "TW":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,1), new Tuple(0,-2), new Tuple(-1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,1), new Tuple(0,-1), new Tuple(-1,0)};
                break;

            case "TS":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,1), new Tuple(-1,0), new Tuple(1,0)};
                break;

            case "LN":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(-1,0), new Tuple(0,0), new Tuple(1,0), new Tuple(1,-1)};
                break;

            case "LE":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(1,1), new Tuple(0,-2), new Tuple(1,-2)};
                squares = new Tuple[] {new Tuple(0,-1), new Tuple(0,0), new Tuple(0,1), new Tuple(1,1)};
                break;

            case "LW":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,1), new Tuple(0,-2), new Tuple(-1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,1), new Tuple(0,-1), new Tuple(-1,-1)};
                break;

            case "LS":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(-1,0), new Tuple(-1,1)};
                break;

            case "JN":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,-1), new Tuple(1,0)};
                break;

            case "JE":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(1,1), new Tuple(0,-2), new Tuple(1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(1,-1), new Tuple(0,1)};
                break;

            case "JW":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,1), new Tuple(0,-2), new Tuple(-1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(-1,1)};
                break;

            case "JS":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(1,1)};
                break;

            case "SE":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(1,1), new Tuple(0,-2), new Tuple(1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(1,0), new Tuple(1,1)};
                break;

            case "SN":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(0,-1), new Tuple(1,-1)};
                break;

            case "SW":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,1), new Tuple(0,-2), new Tuple(-1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,-1), new Tuple(0,1)};
                break;

            case "SS":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(0,1), new Tuple(-1,1)};
                break;

            case "ZN":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(-1,-1)};
                break;

            case "ZE":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(1,1), new Tuple(0,-2), new Tuple(1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(1,-1), new Tuple(0,1)};
                break;

            case "ZW":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(-1,1), new Tuple(0,-2), new Tuple(-1,-2)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(0,-1), new Tuple(-1,0), new Tuple(-1,1)};
                break;

            case "ZS":
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(-1,0), new Tuple(0,1), new Tuple(1,1)};
                break;

            default:
                rotationPoints = new Tuple[] {new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0), new Tuple(0,0)};
                squares = new Tuple[] {new Tuple(0,0), new Tuple(1,0), new Tuple(0,1), new Tuple(1,1)};
                break;
        }

    }

    /**
     * Rotate the tetrimino
     * @param tetrimino
     * @param clockwise
     * @return
     */
    Tetrimino rotateTetrimino(Tetrimino tetrimino, boolean clockwise) {
        // get the next piece
        // increase positionX by rotationPoint number
        int centerX = centerCol + rotationPoints[0].x;
        int centerY = centerRow + rotationPoints[0].y;
        char type = tetrimino.type;
        char orientation = clockwise ? ORIENTATIONS.charAt((ORIENTATIONS.indexOf(tetrimino.orientation) + 1)%4) : ORIENTATIONS.charAt((ORIENTATIONS.indexOf(tetrimino.orientation) + 3)%4);
        return new Tetrimino(centerX, centerY, type, orientation);
    }

    /**
     * get a random tetrimino
     * @return
     */
    public static Tetrimino generateTetrimino() {
        Random random = new Random();
        int index = random.nextInt(TYPES.length());
        char type = TYPES.charAt(index);
        int centerCol = 5;
        int centerRow = 0;
        char orientation = 'N';
        return new Tetrimino(centerCol,centerRow,type,orientation);
    }


    /**
     * Decide on a color for each and every piece
     *
     * @param type
     * @return
     */
     static int chooseColor(char type) {
        switch (type) {
            case 'O':
                return Color.YELLOW;
            case 'I':
                return Color.argb(255, 51, 204, 255);
            case 'J':
                return Color.argb(255, 255, 153, 0);
            case 'L':
                return Color.argb(255, 51, 51, 255);
            case 'T':
                return Color.argb(255, 0, 0, 153);
            case 'S':
                return Color.argb(255, 0, 230, 0);
            case 'Z':
                return Color.argb(255, 255, 0, 0);
            default:
                return Color.WHITE;
        }
    }

    public Canvas drawTetrimino(Canvas canvas, int startX, int startY) {
        for (Tuple tuple : squares) {
            // get the rectangle
            int left = startX + (tuple.x*tuple.SQUARE_WIDTH);
            int top = startY + (tuple.y*tuple.SQUARE_HEIGHT);
            int right = left + tuple.SQUARE_WIDTH;
            int bottom = top + tuple.SQUARE_HEIGHT;
            Rect rect = new Rect(left,top,right,bottom);

            // get the paint to be filled in
            Paint paint = new Paint();
            paint.setColor(chooseColor(type));
            canvas.drawRect(rect,paint);

            // get the border
            Paint strokePaint = new Paint();
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(Color.BLACK);
            strokePaint.setStrokeWidth(5);
            canvas.drawRect(rect,strokePaint);
        }
        return canvas;
    }


    public Tuple[] getSquares() {
        return squares;
    }

    public char getType() {
        return type;
    }

    public int getCenterRow() {
        return centerRow;
    }

    public void setCenterRow(int centerRow) {
        this.centerRow = centerRow;
    }

    public int getCenterCol() {
        return centerCol;
    }

    public void setCenterCol(int centerCol) {
        this.centerCol = centerCol;
    }
}
