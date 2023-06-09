package edu.sdsmt.hamsterrunkellarpatrick.View;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import edu.sdsmt.hamsterrunkellarpatrick.Game;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Bars;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Foodpile;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Home;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Person;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Tube;
import edu.sdsmt.hamsterrunkellarpatrick.Model.Zoom;
import edu.sdsmt.hamsterrunkellarpatrick.R;

/*
 * Author: Patrick Kellar
 * Description: Hub class for rendering the playable area
 * */
public class GameView extends View {

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 5;
    private Paint borderPaint;
    private int boxSize;
    private Game game = new Game();
    private int hamsterColor = -1;
    private Bitmap hamsterImage;
    private Paint hamsterPaint;
    private Paint paint;

    //context is an interface to global info about the application
    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Description: Removes the hamsters current color
     * */
    public void clearHamsterColor() {
        //set the hamster's color filter to nothing
        hamsterPaint.setColorFilter(null);

        //Show that the color is not set
        hamsterColor = -1;
    }

    /**
     * Description: Draw the hamster in this view
     *
     * @param canvas the canvas being used
     * @param offsetX how much the hamster needs to be moved over in the x direction
     * @param offsetY how much the hamster needs to be moved over in the y direction
     * */
    private void drawHamster(Canvas canvas, int offsetX, int offsetY) {
        //get the smaller dimension
        float size = (float) Math.min(getWidth(), getHeight());
        size = size * 0.15f;

        //calculate grid area width and height
        int areaWid = Math.min(getWidth(), getHeight()) / NUM_COLUMNS;
        int areaHit = Math.min(getWidth(), getHeight()) / NUM_ROWS;

        //calculate hamster target size
        float imageSizeW = hamsterImage.getWidth();
        float imageSizeH = hamsterImage.getHeight();
        Point loc = game.getPlayerLocation();
        float scaleFactor = size / Math.max(imageSizeW, imageSizeH);

        //get x and y center location
        float x = (loc.x * areaWid + (float) areaWid / 2 - imageSizeW / 2 * scaleFactor) + offsetX;
        float y = (loc.y * areaHit + (float) areaHit / 2 - imageSizeH / 2 * scaleFactor) + offsetY;

        //use graphics matrix to place hamster correctly
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(hamsterImage, 0, 0, hamsterPaint);
        canvas.restore();
    }

    /**
     * Description: Get the color of the hamster
     *
     * @return the hamster's current color
     * */
    public int getHamsterColor() {
        return hamsterColor;
    }

    /**
     * Description: Get the Paint objects, and the hamster image
     * */
    public void init() {
        //paint for the gird
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        //paint for the border
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(6);
        borderPaint.setColor(Color.RED);

        //paint for the hamster
        hamsterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        hamsterImage = BitmapFactory.decodeResource(getResources(), R.drawable.hamster);
    }

    /**
     * Description: Draw the game grid with the hamster in its respective position
     *
     * @param canvas the canvas being used for this view
     * */
    @Override
    public void onDraw(Canvas canvas) {

        // Draw the main box
        int boxWidth = boxSize * NUM_COLUMNS;
        int boxHeight = boxSize * NUM_ROWS;
        int startX = (getWidth() - boxWidth) / 2;
        int startY = (getHeight() - boxHeight) / 2;
        int endX = startX + boxWidth;
        int endY = startY + boxHeight;
        canvas.drawRect(startX, startY, endX, endY, paint);

        // If there are zoom moves left for the hamster, draw a red border
        if (game.getZoomMovesLeft() > 0) {
            canvas.drawRect(startX, startY, endX, endY, borderPaint);
        }

        //Calculate how much white space around the grid
        int offsetX = (getWidth() - (endX - startX)) / 2;
        int offsetY = (getHeight() - (endY - startY)) / 2;

        // Calculate the size of the inner boxes with padding
        int innerBoxSize = (int) Math.round((boxSize) * 0.9);

        // Draw the smaller boxes inside the main box
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLUMNS; col++) {

                //Set the color of the paint to its corresponding tile class
                if (Bars.class.equals(game.getTileClass(col, row))) {
                    paint.setColor(Color.GRAY);
                } else if (Tube.class.equals(game.getTileClass(col, row))) {
                    paint.setColor(Color.BLUE);
                } else if (Foodpile.class.equals(game.getTileClass(col, row))) {
                    //This should be brown
                    paint.setColor(Color.rgb(150, 75, 0));
                } else if (Home.class.equals(game.getTileClass(col, row))) {
                    paint.setColor(Color.GREEN);
                } else if (Person.class.equals(game.getTileClass(col, row))) {
                    paint.setColor(Color.YELLOW);
                } else if (Zoom.class.equals(game.getTileClass(col, row))) {
                    paint.setColor(Color.MAGENTA);
                }

                //calculate the starting x and y of the tile
                int x = startX + col * boxSize + 5;
                int y = startY + row * boxSize + 5;
                canvas.drawRect(x, y, x + innerBoxSize, y + innerBoxSize, paint);

                //set the paint color back to black
                paint.setColor(Color.BLACK);
            }
        }

        //draw the hamster where it is on the grid
        drawHamster(canvas, offsetX, offsetY);
    }

    /**
     * Description: Get the box size and redraw the view
     *
     * @param w the new width
     * @param h the new height
     * @param oldw the old width
     * @param oldh the new height
     * */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Update the box size when the view size changes
        boxSize = Math.min(w, h) / Math.max(NUM_ROWS, NUM_COLUMNS);
        // Invalidate the view to trigger a redraw
        invalidate();
    }

    /**
     * Description: Set the current game
     *
     * @param game the current game
     * */
    public void setGame(Game game) {
        //set the game and redraw
        this.game = game;
        invalidate();
    }

    /**
     * Description: Set the color of the hamster
     *
     * @param color the new color of the hamster
     * */
    public void setHamsterColor(int color) {
        //apply the new color filter and set the color
        hamsterPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        hamsterColor = color;
        invalidate();
    }
}
