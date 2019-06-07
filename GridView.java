package com.example.mygameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * The main view for MyGameOfLife.
 * Draws a grid of Cells.
 */
public class GridView extends View {

    int xMin = 0;
    int yMin = 0;
    int xMax;
    int yMax;

    /**
     * The Cell grid.
     */
    Cell[][] grid;

    /**
     * The width of the grid.
     */
    int w = 20;

    /**
     * The height of the grid
     */
    int h = 20;

    /**
     * The size of a single Cell on the screen.
     */
    int size = 20;

    public GridView(Context context){
        super(context);

        //Creates new grid.
        grid = new Cell[h][w];

        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){

                //Initializes each Cell in the grid.
                grid[y][x] = new Cell((x + y) / 8.0);

            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){

        Paint paint = new Paint();

        //Draws each Cell to the screen.
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                paint.setColor(grid[y][x].color());
                canvas.drawRect(x * this.size, y * this.size, (x + 1) * this.size, (y + 1) * this.size, paint);
            }
        }
        
        updateGrid();
        
        try{
            Thread.sleep(30);
        } catch (InterruptedException e) {}
        
        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        xMax = w - 1;
        yMax = h - 1;
    }
    
    public void updateGrid(){
        
    }
    
}
