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

    /**
     * A constant determining how energy flows within the grid.
     */
    float k = 0.265;

    /**
     * Constant determining how much energy is added to the system.
     */
    float c = 0.1f;
    
    Random random;
    
    /**
     * The size of a single Cell on the screen.
     */
    int size = 20;

    public GridView(Context context){
        super(context);

        //Creates new grid.
        grid = new Cell[h][w];

        random = new Random();

        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){

                //Initializes each Cell in the grid.
                grid[y][x] = new Cell(random.nextInt(6));

            }
        }
        
        //Adds one living cell to the grid.
        LivingCell initial = new LivingCell(0.8f, 0.5f, grid, 10.0f, false, Color.GREEN);
        grid[h / 2][w / 2] = initial;
        initial.setLocation(h / 2, w / 2);
        
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
            Thread.sleep(50);
        } catch (InterruptedException e) {}

        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        xMax = w - 1;
        yMax = h - 1;
        
        //Randomly changes parameters whenever screen size is changed.
        k = 0.1f * random.nextFloat() + 0.2f;
        c = 0.1f * random.nextFloat();
        LivingCell.energyPerAction = 0.1f * random.nextFloat() + 0.05f;
        LivingCell.mutationRate = 0.1f * random.nextFloat();
      
    }

    public void updateGrid(){

        float[][] newGrid = new double[h][w];
        int neighbors;
        float newEnergy = 0.0;


        for(int y =0; y < h; y++){
            for(int x = 0; x < w; x++){

                if(grid[y][x] instanceof LivingCell){
                    continue;
                }
                
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
        
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                Cell cell = grid[y][x];
                if(cell instanceof LivingCell){
                    LivingCell lCell = (LivingCell) cell;
                    lCell.update();
                }
            }
        }
        
        
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                Cell cell = grid[y][x];
                if(cell instanceof LivingCell){
                    LivingCell lCell = (LivingCell) cell;
                    lCell.refresh();
                }
            }
        }
    }
}
