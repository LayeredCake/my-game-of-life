package com.example.mygameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

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
    int w = 24;

    /**
     * The height of the grid
     */
    int h = 34;

    //0.25: fades to white
    //0.26: fades to white
    //0.265: fades to white
    //0.27: fades to black
    //0.275: fades to black
    //0.3: fades to black
    //0.325: fades to black
    //0.35: checkers and lines
    double k = 0.265;

    /**
     * The size of a single Cell on the screen.
     */
    int size = 20;

    public GridView(Context context){
        super(context);

        //Creates new grid.
        grid = new Cell[h][w];

        Random random = new Random();

        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){

                //Initializes each Cell in the grid.
                grid[y][x] = new Cell(random.nextInt(6));

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
            Thread.sleep(100);
        } catch (InterruptedException e) {}

        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        xMax = w - 1;
        yMax = h - 1;
    }

    public void updateGrid(){

        double[][] newGrid = new double[h][w];
        int neighbors;
        double newEnergy = 0.0;


        for(int y =0; y < h; y++){
            for(int x = 0; x < w; x++){

                neighbors = 4;

                if(x <= 0){
                    neighbors--;
                }else{
                    newEnergy += grid[y][x-1].getEnergy();
                }

                if(x >= w - 1){
                    neighbors--;
                }else{
                    newEnergy += grid[y][x+1].getEnergy();
                }

                if(y <= 0){
                    neighbors--;
                }else{
                    newEnergy += grid[y-1][x].getEnergy();
                }

                if(y >= h - 1){
                    neighbors--;
                }else{
                    newEnergy += grid[y+1][x].getEnergy();
                }

                if(neighbors == 0){
                    newEnergy = grid[y][x].getEnergy();
                }else{
                    newEnergy = newEnergy / neighbors - k * grid[y][x].getEnergy();
                }

                newGrid[y][x] = newEnergy;

            }
        }

        for(int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                grid[y][x].setEnergy(newGrid[y][x]);
            }
        }

    }
}
